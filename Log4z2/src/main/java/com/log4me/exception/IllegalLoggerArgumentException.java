package com.log4me.exception;

/**
 * @author zhangxuecheng4441
 * @date 2021/5/19/019 14:21
 */
public class IllegalLoggerArgumentException  extends RuntimeException {

    private static final long serialVersionUID = 1121238490818316276L;

    public IllegalLoggerArgumentException() {
        super();
    }

    public IllegalLoggerArgumentException(String s) {
        super(s);
    }

    public IllegalLoggerArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalLoggerArgumentException(Throwable cause) {
        super(cause);
    }
}
