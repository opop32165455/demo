package com.log4me.core.level;

/**
 * @author zhangxuecheng4441
 * @date 2021/5/18/018 17:17
 */
public interface ErrorLog {
    /**
     * 是否开启
     *
     * @return ERROR 等级是否开启
     */
    boolean isErrorEnabled();

    /**
     * 打印 ERROR 等级的日志
     *
     * @param t 错误对象
     */
    void error(Throwable t);

    /**
     * 打印 ERROR 等级的日志
     *
     * @param format 消息模板
     * @param arguments 参数
     */
    void error(String format, Object... arguments);

}
