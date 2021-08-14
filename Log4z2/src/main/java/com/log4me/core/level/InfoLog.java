package com.log4me.core.level;

/**
 * @author zhangxuecheng4441
 * @date 2021/5/18/018 17:17
 */
public interface InfoLog {
    /**
     * 是否开启
     *
     * @return INFO 等级是否开启
     */
    boolean isInfoEnabled();

    /**
     * 打印 INFO 等级的日志
     *
     * @param t 错误对象
     */
    void info(Throwable t);

    /**
     * 打印 INFO 等级的日志
     *
     * @param format 消息模板
     * @param arguments 参数
     */
    void info(String format, Object... arguments);

}
