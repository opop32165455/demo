package com.log4me.core.Factory;

import cn.hutool.core.util.StrUtil;
import com.log4me.core.level.Level;
import com.log4me.core.producer.DefaultProducer;
import com.log4me.exception.IllegalLoggerArgumentException;
import org.springframework.util.ObjectUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhangxuecheng4441
 * @date 2021/5/19/019 13:55
 */
public class DefaultLogFactory implements LogFactory {

    /**
     * 日志对象缓存
     */
    private Map<Object, DefaultProducer> logCache = new ConcurrentHashMap<>();

    /**
     * 获得日志对象
     *
     * @param clazz 日志对应类
     * @return 日志对象
     */
    @Override
    public DefaultProducer getLog(Class<?> clazz) {
        DefaultProducer log = logCache.get(clazz);
        if (null == log) {
            log = create(clazz);
            logCache.put(clazz, log);
        }
        return log;
    }

    /**
     * 获得日志对象
     *
     * @param name 日志对象名
     * @return 日志对象
     */
    @Override
    public DefaultProducer getLog(String name) {
        DefaultProducer log = logCache.get(name);
        if (null == log) {
            //log = create(name);
            logCache.put(name, log);
        }
        return log;
    }

    @Override
    public DefaultProducer create(String name) {
        if (StrUtil.isBlank(name)) {
            throw new IllegalLoggerArgumentException("logger name is blank!");
        }
        return new DefaultProducer(Level.ALL, name);
    }

    @Override
    public DefaultProducer create(Class<?> clazz) {
        if (ObjectUtils.isEmpty(clazz)) {
            throw new IllegalLoggerArgumentException("logger clazz is blank!");
        }
        String clazzName = clazz.getName();
        return new DefaultProducer(Level.ALL, clazzName);
    }
}
