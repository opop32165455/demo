package com.threads.config;

import cn.hutool.core.thread.ThreadUtil;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

/**
 * todo 简单抽取线程池 后期完善线程池其他参数
 * 59.130/80 环境 CPU 参数 ：Intel(R) Xeon(R) CPU E5-2683 v3 @ 2.00GHz （14核 28线程 CPU）
 *
 * @author zhangxuecheng4441
 * @date 2021/3/30/030 11:18
 */
@Component
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
                    simpleThreadPool = ThreadUtil.newExecutor(6, 15);
                }
            }
        }
        return simpleThreadPool;
    }

    public static ExecutorService getSingleThreadPool() {
        if (singleThreadPool == null) {
            synchronized (ThreadPools.class) {
                if (singleThreadPool == null) {
                    singleThreadPool = ThreadUtil.newExecutor(1, 1);
                }
            }
        }
        return simpleThreadPool;
    }
}
