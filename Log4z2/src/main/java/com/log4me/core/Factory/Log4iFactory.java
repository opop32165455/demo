package com.log4me.core.Factory;

import com.log4me.config.LogManager;
import com.log4me.core.producer.DefaultProducer;


/**
 * @author zhangxuecheng4441
 * @date 2021/5/18/018 16:44
 */
public abstract class Log4iFactory {



    public static DefaultProducer getLogger(String name) {
        LogFactory iLoggerFactory = getILoggerFactory();
        return iLoggerFactory.getLog(name);
    }

    public static DefaultProducer getLogger(Class<?> clazz) {
        LogFactory iLoggerFactory = getILoggerFactory();
        return iLoggerFactory.getLog(clazz);
    }

    private static LogFactory getILoggerFactory() {
        return LogManager.getLogFactory();
    }



}
