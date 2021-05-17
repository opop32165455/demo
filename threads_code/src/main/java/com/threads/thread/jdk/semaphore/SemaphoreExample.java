package com.threads.thread.jdk.semaphore;

import com.threads.config.ThreadPools;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author zhangxuecheng4441
 * @date 2021/5/17/017 19:45
 */
@Slf4j
public class SemaphoreExample {
    static ExecutorService simpleThreadPool = new ThreadPools().getSimpleThreadPool();
    public static void main(String[] args) {

        //execute task count in the same time
        final int parallelCount = 3;
        //task task controller
        final SemaphoreController semaphoreController = new SemaphoreController(parallelCount, true);
        //task count
        IntStream allTask = IntStream.range(0, 10);
        allTask.forEach(i ->
                simpleThreadPool.execute(() -> {
                    try {
                        //todo debug 运行任务 其他任务拿不到凭证则
                        boolean isGetStamp = semaphoreController.executeTask(1, 10);
                        //获取凭证 则可以执行任务
                        if (isGetStamp) {
                            //执行任务
                            executeSomeTask(i);
                        } else {
                            log.warn("can not execute task {}", i);
                            //执行服务降级方案
                            return;
                        }
                        //todo debug 运行任务 如果拿不到凭证 则堵塞任务 等待可以获得凭证 则执行
                        //semaphoreController.executeTaskUtilSuccessOrInterrupted(1);
                        //executeSomeTask(i);
                    } catch (Exception e) {
                        log.error("execute error", e);
                        //其他任务发生异常时 return 不会执行下面的提交任务凭证
                        return;
                    }
                    //todo 因为semaphore是任务共享的 其他线程发送异常后
                    //todo 也会执行endTask(release) 会出现非执行任务线程 提交任务结束的bug
                    //拆分开 try-catch-finally 其他任务发生异常时 return 不会执行下面的提交任务凭证
                    try {
                        log.info("finish task");
                    } finally {
                        //任务声明结束 务必放在finally中 否则在排查问题上会有很多问题
                        semaphoreController.endTask(1);
                        log.info("NO.{} task end ", i);
                    }
                })
        );


    }

    private static void executeSomeTask(int taskNumber) {
        try {
            int taskSpendTime = new Random().nextInt(20);
            log.info("execute NO.[{}] task need spend {} s", taskNumber, taskSpendTime);
            TimeUnit.SECONDS.sleep(taskSpendTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Data
    @Builder
    @AllArgsConstructor
    private static class SemaphoreController {
        final int initCount = 2;
        private final Semaphore semaphore;


        /**
         * 定义Semaphore指定许可证数量，并且指定非公平的同步器，
         * 因此new Semaphore(n)实际上是等价于newSemaphore(n，false)的。
         *
         * @param customStampCount customStampCount
         * @param failQueue        定义Semaphore指定许可证数量的同时给定非公平或是公平同步器。
         */
        public SemaphoreController(int customStampCount, boolean failQueue) {
            this.semaphore = new Semaphore(customStampCount, failQueue);
        }

        /**
         * 在使用无参的tryAcquire时只会向Semaphore尝试获取一个许可证，
         * 但是该方法会向Semaphore尝试获取指定数目的许可证。
         * <p>
         * 该方法与tryAcquire无参方法类似，同样也是尝试获取一个许可证，但是增加了超时参数。
         * 如果在超时时间内还是没有可用的许可证，那么线程就会进入[阻塞状态]，
         * 直到到达超时时间或者在超时时间内有可用的证书（被其他线程释放的证书），
         * 或者阻塞中的线程被其他线程执行了中断。
         *
         * @return isTaskStart
         */
        public boolean executeTask(int stampCount, int timeoutSeconds) {
            boolean isTaskStart = false;
            try {
                isTaskStart = semaphore.tryAcquire(stampCount, timeoutSeconds, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return isTaskStart;
        }

        /**
         * 提交任务 返回标记
         *
         * @param stampCount stampCount
         */
        public void endTask(int stampCount) {
            try {
                semaphore.release(stampCount);
            } catch (Exception e) {
                log.error("error release");
            }
        }

        /**
         * acquire方法也是向Semaphore获取许可证，但是该方法比较偏执一些，
         * 获取不到就会一直等（陷入阻塞状态），Semaphore为我们提供了acquire方法的两种重载形式。
         * <p>
         * 直到Semaphore有可用的许可证为止，或者被其他线程中断。
         * 当然，如果有可用的许可证则会立即返回
         *
         * @param stampCount taskCount
         */
        public void executeTaskUtilSuccessOrInterrupted(int stampCount) {
            try {
                semaphore.acquire(stampCount);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /**
         * 该方法会向Semaphore获取一个许可证，如果获取不到就会一直等待，
         * 与此同时对该线程的任何中断操作都会被无视，
         * 直到Semaphore有可用的许可证为止。当然，如果有可用的许可证则会立即返回。
         *
         * @param stampCount stampCount
         */
        public void forceExecuteTaskUtilSuccess(int stampCount) {
            semaphore.acquireUninterruptibly(stampCount);
        }

        /**
         * 对Semaphore许可证的争抢采用公平还是非公平的方式，
         * 对应到内部的实现类为FairSync（公平）和NonfairSync（非公平）。
         *
         * @return isFair
         */
        public boolean isFair() {
            return semaphore.isFair();
        }

        /**
         * 当前的Semaphore还有多少个可用的许可证。
         *
         * @return 当前的Semaphore还有多少个可用的许可证。
         */
        public int availablePermits() {
            return semaphore.availablePermits();
        }

        /**
         * 排干Semaphore的所有许可证，以后的线程将无法获取到许可证，
         * 已经获取到许可证的线程将不受影响。
         *
         * @return 排干Semaphore的所有许可证
         */
        public int drainPermits() {
            return semaphore.drainPermits();
        }

        /**
         * 当前是否有线程由于要获取Semaphore许可证而进入阻塞？（该值为预估值。）
         *
         * @return 当前是否有线程由于要获取Semaphore许可证而进入阻塞？（该值为预估值。）
         */
        boolean hasQueuedThreads() {
            return semaphore.hasQueuedThreads();
        }

        /**
         * 如果有线程由于获取Semaphore许可证而进入阻塞，那么它们的个数是多少呢？（该值为预估值。）
         *
         * @return 如果有线程由于获取Semaphore许可证而进入阻塞，那么它们的个数是多少呢？（该值为预估值。）
         */
        int getQueueLength() {
            return semaphore.getQueueLength();
        }
    }
}
