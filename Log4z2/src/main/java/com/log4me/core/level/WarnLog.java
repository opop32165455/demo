package com.log4me.core.level;

/**
 * @author zhangxuecheng4441
 * @date 2021/5/18/018 17:18
 */
public interface WarnLog {

    /**
     * WARN 等级是否开启
     *
     * @return WARN 等级是否开启
     */
    boolean isWarnEnabled();

    /**
     * 打印 WARN 等级的日志
     *
     * @param t 错误对象
     */
    void warn(Throwable t);

    /**
     * 打印 WARN 等级的日志
     *
     * @param format 消息模板
     * @param arguments 参数
     */
    void warn(String format, Object... arguments);

}
