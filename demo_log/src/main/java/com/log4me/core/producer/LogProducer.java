package com.log4me.core.producer;

import com.log4me.core.level.DebugLog;
import com.log4me.core.level.ErrorLog;
import com.log4me.core.level.InfoLog;
import com.log4me.core.level.WarnLog;

/**
 * @author zhangxuecheng4441
 * @date 2021/5/18/018 16:56
 */
public interface LogProducer extends DebugLog, ErrorLog, InfoLog, WarnLog {
    /**
     * debug 日志
     *
     * @param msg 信息
     */
    public void debug(String msg);

    /**
     * info 日志
     *
     * @param msg 信息
     */
    public void info(String msg);

    /**
     * warn 日志
     *
     * @param msg 信息
     */
    public void warn(String msg);

    /**
     * error 日志
     *
     * @param msg 信息
     */
    public void error(String msg);

}
