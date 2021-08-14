package com.threads.thread.jdk.semaphore;

import com.threads.config.ThreadPools;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author zhangxuecheng4441
 * @date 2021/5/17/017 21:45
 */
@Slf4j
public class MySemaphoreExample {
    static ExecutorService simpleThreadPool = new ThreadPools().getSimpleThreadPool();
    /**
     * execute task count in the same time
     */
    final static int PARALLEL_COUNT = 3;
    /**
     * 一次任务获取凭证数量
     */
    final static int ONCE_PERMITS = 1;
    /**
     * 一共十个任务
     */
    final static IntStream ALL_TASK = IntStream.range(0, 10);
    /**
     * 任务获取超时时间10s
     */
    final static int OUT_TIME = 10;

    public static void main(String[] args) {
        MySemaphoreHandler<Integer, Integer> semaphoreHandler = new MySemaphoreHandler<>(new MySemaphore(PARALLEL_COUNT),
                MySemaphoreExample::executeSomeTaskFunction, MySemaphoreExample::dropMethodFunction,
                ONCE_PERMITS, OUT_TIME, TimeUnit.SECONDS);
        //执行任务
        ALL_TASK.forEach(i ->
                simpleThreadPool.execute(
                        //() -> semaphoreHandler.handle(i)
                        () -> semaphoreHandler.forceHandle(i)
                )
        );
    }

    /**
     * 主要任务执行逻辑
     *
     * @param taskNumber
     * @return
     */
    public static Integer executeSomeTaskFunction(Integer taskNumber) {
        try {
            int taskSpendTime = new Random().nextInt(20);
            log.info("execute NO.[{}] task need spend {} s", taskNumber, taskSpendTime);
            TimeUnit.SECONDS.sleep(taskSpendTime);
            return taskSpendTime;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 降级方法
     *
     * @param taskNumber
     * @return
     */
    private static Integer dropMethodFunction(Integer taskNumber) {
        try {
            int taskSpendTime = new Random().nextInt(20);
            log.info("execute dropMethodFunction NO.[{}] task need spend {} s", taskNumber, taskSpendTime);
            TimeUnit.SECONDS.sleep(taskSpendTime);
            return taskSpendTime;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
