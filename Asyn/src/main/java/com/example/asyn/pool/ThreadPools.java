package com.example.asyn.pool;

import cn.hutool.core.thread.ExecutorBuilder;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * todo 简单抽取线程池 后期完善线程池其他参数
 * CPU 参数 ：Intel(R) Xeon(R) CPU E5-2683 v3 @ 2.00GHz （14核 28线程 CPU）
 *
 * @author zhangxuecheng4441
 * @date 2021/3/30/030 11:18
 */
public class ThreadPools {
    /**
     * 处理多线程任务（速度快 消耗大）
     * 但是如果任务繁重 相当消耗系统线程资源
     */
    private static volatile ExecutorService simpleThreadPool;
    /**
     * 处理繁琐的单线程任务（速度慢 消耗小）
     * 不那么浪费线程资源
     */
    private static volatile ExecutorService singleThreadPool;

    public static ExecutorService getSimpleThreadPool() {
        if (simpleThreadPool == null) {
            synchronized (ThreadPools.class) {
                if (simpleThreadPool == null) {
                    simpleThreadPool = ExecutorBuilder.create()
                            .setCorePoolSize(6)
                            .setMaxPoolSize(15)
                            .setThreadFactory(new CustomThreadFactory())
                            .build();;
                }
            }
        }
        return simpleThreadPool;
    }

    public static ExecutorService getSingleThreadPool() {
        if (singleThreadPool == null) {
            synchronized (ThreadPools.class) {
                if (singleThreadPool == null) {
                    singleThreadPool = ExecutorBuilder.create()
                            .setCorePoolSize(1)
                            .setMaxPoolSize(4)
                            .setThreadFactory(new CustomThreadFactory())
                            .build();
                }
            }
        }
        return simpleThreadPool;
    }

    static class CustomThreadFactory implements ThreadFactory {
        private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        CustomThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = "custom-pool-" +
                    POOL_NUMBER.getAndIncrement() +
                    "-thread-";
        }

        @Override
        public Thread newThread(@NonNull Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }
}
