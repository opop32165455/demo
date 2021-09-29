package com.log4me.core.level;

/**
 * @author zhangxuecheng4441
 * @date 2021/5/18/018 17:17
 */
public interface DebugLog {
    /**
     * 是否开启
     *
     * @return DEBUG 等级是否开启
     */
    boolean isDebugEnabled();

    /**
     * 打印 DEBUG 等级的日志
     *
     * @param t 错误对象
     */
    void debug(Throwable t);

    /**
     * 打印 DEBUG 等级的日志
     *
     * @param format 消息模板
     * @param arguments 参数
     */
    void debug(String format, Object... arguments);

}
