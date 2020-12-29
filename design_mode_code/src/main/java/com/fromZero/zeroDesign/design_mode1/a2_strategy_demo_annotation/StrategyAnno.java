package com.fromZero.zeroDesign.design_mode1.a2_strategy_demo_annotation;

import java.lang.annotation.*;

/**
 * @Desciption:
 * @Auther: ZhangXueCheng4441
 * @Date:2020/12/12/012 17:44
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})//作用范围
@Documented//生效到文档
public @interface StrategyAnno {
    //todo 注解+aop也可以实现注册
    //Handler handle();
    String value();
}
