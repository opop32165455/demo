package com.threads.thread.jdk.semaphore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @author zhangxuecheng4441
 * @date 2021/5/17/017 20:10
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MySemaphoreHandler<T, R> {
    private static final long serialVersionUID = 3184061773358565644L;
    Semaphore semaphore;
    /**
     * 需要执行的函数
     */
    Function<T, R> executeTaskFunction;
    /**
     * 线程堵塞降级方法
     */
    Function<T, R> dropMethodFunction;
    /**
     * 一次任务凭证数量
     */
    int permits;
    /**
     * 超时时间
     */
    long timeout;
    /**
     * 超时时间单位
     */
    TimeUnit unit;

    /**
     * 执行任务 如果任务失败失败
     * 则执行降级方法
     */
    public R handle(T parameter) {
        R result = null;
        try {
            //运行任务 其他任务拿不到凭证则
            boolean isGetStamp = semaphore.tryAcquire(permits, timeout, unit);
            //获取凭证 则可以执行任务
            if (isGetStamp) {
                //执行任务
                result = executeTaskFunction.apply(parameter);
            } else {
                result = dropMethodFunction.apply(parameter);
                //执行服务降级方案
                return result;
            }
        } catch (Exception e) {
            log.error("execute error", e);
            //其他任务发生异常时 return 不会执行下面的提交任务凭证
            return null;
        }
        //todo 因为semaphore是任务共享的 其他线程发送异常后
        //todo 也会执行endTask(release) 会出现非执行任务线程 提交任务结束的bug
        //todo 使用重写的 { MySemaphore } 可以直接避免问题
        //拆分开 try-catch-finally 其他任务发生异常时 return 不会执行下面的提交任务凭证
        try {
            log.info("No.{} finish task", parameter);
        } finally {
            //任务声明结束 务必放在finally中 否则在排查问题上会有很多问题
            semaphore.release(permits);
        }
        return result;
    }

    /**
     * 强行执行所有任务
     * 任务无法获得凭证 堵塞线程至能获得为止
     */
    public R forceHandle(T parameter) {
        R result = null;
        try {
            semaphore.acquire();
            result = executeTaskFunction.apply(parameter);
        } catch (Exception e) {
            log.error("execute error", e);
            //其他任务发生异常时 return 不会执行下面的提交任务凭证
            return null;
        }
        //todo 因为semaphore是任务共享的 其他线程发送异常后
        //todo 也会执行endTask(release) 会出现非执行任务线程 提交任务结束的bug
        //todo 使用重写的 { MySemaphore } 可以直接避免问题
        //拆分开 try-catch-finally 其他任务发生异常时 return 不会执行下面的提交任务凭证
        try {
            log.info("No.{} finish task", parameter);
        } finally {
            //任务声明结束 务必放在finally中 否则在排查问题上会有很多问题
            semaphore.release(permits);
        }
        return result;
    }

}
