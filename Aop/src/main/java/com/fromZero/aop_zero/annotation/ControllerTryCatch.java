package com.fromZero.aop_zero.annotation;

import java.lang.annotation.*;

/**
 * @author zhangxuecheng4441
 * @date 2021/1/9/009 11:10
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface ControllerTryCatch {
    String ControllerMethodName();
}
