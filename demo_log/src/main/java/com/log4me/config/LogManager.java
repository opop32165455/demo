package com.log4me.config;

import com.log4me.core.Factory.DefaultLogFactory;
import com.log4me.core.Factory.LogFactory;
import com.log4me.core.consumer.ConsumerRunnable;
import com.log4me.core.consumer.LogConsumer;
import com.log4me.core.queue.DefaultLogQueue;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangxuecheng4441
 * @date 2021/5/19/019 11:46
 */
public class LogManager {

    final static boolean isStart = true;

    private static LogFactory logFactory;

    private static Queue<?> queue = new ConcurrentLinkedQueue<>();

    private static LogConsumer<Queue<?>> consumer;

    private final static ScheduledThreadPoolExecutor CONSUMER_CONTROLLER = new ScheduledThreadPoolExecutor(1, r -> new Thread(r, "CONSUMER_CONTROLLER"));

    public static ScheduledThreadPoolExecutor getConsumerController() {
        return CONSUMER_CONTROLLER;
    }

    public static boolean isIsStart() {
        return isStart;
    }

    public static void main(String[] args) {
        if (isStart) {
            CONSUMER_CONTROLLER.scheduleAtFixedRate(new ConsumerRunnable(), 1, 5, TimeUnit.SECONDS);
        }
    }

    public static Queue<?> getQueue() {
        if (queue == null) {
            queue = new DefaultLogQueue<>();
        }
        return queue;
    }

    public static void setQueue(Queue<?> queue) {
        LogManager.queue = queue;
    }

    public static LogConsumer<Queue<?>> getConsumer() {
        if (consumer == null) {
            consumer = new LogConsumer<Queue<?>>();
        }
        return consumer;
    }


    public static LogFactory getLogFactory() {
        if (logFactory == null) {
            return new DefaultLogFactory();
        }
        return logFactory;
    }

    public static void setLogFactory(LogFactory logFactory) {
        LogManager.logFactory = logFactory;
    }


}
