package com.log4me.core.producer;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.log4me.config.LogManager;
import com.log4me.core.level.Level;

import java.io.Serializable;
import java.util.Queue;

/**
 * 直接打印到本地
 *
 * @author zhangxuecheng4441
 * @date 2021/5/18/018 19:55
 */
public class DefaultProducer implements LogProducer, Serializable {

    private static final long serialVersionUID = -8398236012479713037L;

    transient private Level level;

    private String name;

    Queue<Object> queue = (Queue<Object>) LogManager.getQueue();;

    public DefaultProducer(Level level, String name) {
        this.level = level;
        this.name = name;
    }

    String logPrefix(Level level) {
        return StrUtil.format("[{}:LOG] {} {} [{}] ", name, level, DateUtil.now(), Thread.currentThread().getName());
    }


    @Override
    public void debug(String msg) {
        if (level == Level.ALL || level == Level.DEBUG) {
            queue.add(logPrefix(Level.DEBUG) + msg);
        }

    }

    @Override
    public void debug(Throwable t) {
        if (level == Level.ALL || level == Level.DEBUG) {
            queue.add(logPrefix(Level.DEBUG) + t.getMessage());
        }
    }

    @Override
    public void debug(String format, Object... arguments) {
        if (level == Level.ALL || level == Level.DEBUG) {
            queue.add(logPrefix(Level.DEBUG) + StrUtil.format(format, arguments));
        }
    }

    @Override
    public void info(String msg) {
        if (level == Level.ALL || level == Level.INFO) {
            queue.add(logPrefix(Level.INFO) + msg);
        }
    }

    @Override
    public void info(Throwable t) {
        if (level == Level.ALL || level == Level.INFO) {
            queue.add(logPrefix(Level.INFO) + t.getMessage());
        }
    }

    @Override
    public void info(String format, Object... arguments) {
        if (level == Level.ALL || level == Level.INFO) {
            queue.add(logPrefix(Level.INFO) + StrUtil.format(format, arguments));
        }
    }


    @Override
    public void warn(String msg) {
        if (level == Level.ALL || level == Level.WARN) {
            queue.add(logPrefix(Level.WARN) + msg);
        }
    }

    @Override
    public void warn(Throwable t) {
        if (level == Level.ALL || level == Level.WARN) {
            queue.add(logPrefix(Level.WARN) + t.getMessage());
        }
    }

    @Override
    public void warn(String format, Object... arguments) {
        if (level == Level.ALL || level == Level.WARN) {
            queue.add(logPrefix(Level.WARN) + StrUtil.format(format, arguments));
        }
    }

    @Override
    public void error(String msg) {
        if (level == Level.ALL || level == Level.ERROR) {
            queue.add(logPrefix(Level.ERROR) + msg);
        }
    }


    @Override
    public void error(Throwable t) {
        if (level == Level.ALL || level == Level.ERROR) {
            queue.add(logPrefix(Level.ERROR) + t.getMessage());
        }
    }

    @Override
    public void error(String format, Object... arguments) {
        if (level == Level.ALL || level == Level.ERROR) {
            queue.add(logPrefix(Level.ERROR) + StrUtil.format(format, arguments));
        }
    }


    @Override
    public boolean isDebugEnabled() {
        return false;
    }

    @Override
    public boolean isErrorEnabled() {
        return false;
    }

    @Override
    public boolean isInfoEnabled() {
        return false;
    }

    @Override
    public boolean isWarnEnabled() {
        return false;
    }

}
