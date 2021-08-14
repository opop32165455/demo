package com.log4me.core.Factory;

import com.log4me.core.producer.DefaultProducer;

/**
 * @author zhangxuecheng4441
 * @date 2021/5/18/018 18:10
 */
public interface LogFactory {
    /**
     *
     * @param clazz
     * @return
     */
    DefaultProducer getLog(Class<?> clazz);

    /**
     *
     * @param name
     * @return
     */
    DefaultProducer getLog(String name);


    DefaultProducer create(String name);

    DefaultProducer create(Class<?> clazz);
}
