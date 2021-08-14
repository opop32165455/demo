package com.log4me.core.consumer;

import com.log4me.config.LogManager;
import com.log4me.exception.IllegalLoggerArgumentException;

import java.util.Queue;

/**
 * @author zhangxuecheng4441
 * @date 2021/5/21/021 10:35
 */
public class ConsumerRunnable implements Runnable {

    @Override
    public void run() {
        Queue<?> queue = LogManager.getQueue();
        if (queue == null) {
            throw new IllegalLoggerArgumentException("queue is null consumer can not start");
        }

        LogConsumer<Queue<?>> consumer = LogManager.getConsumer();
        if (consumer == null) {
            throw new IllegalLoggerArgumentException("consumer is null consumer can not start");
        }
        consumer.before(consumer).accept(queue);

    }


}
