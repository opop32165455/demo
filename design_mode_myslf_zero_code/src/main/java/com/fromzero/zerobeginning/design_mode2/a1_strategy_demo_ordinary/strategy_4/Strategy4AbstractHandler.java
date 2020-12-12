package com.fromzero.zerobeginning.design_mode2.a1_strategy_demo_ordinary.strategy_4;

import com.fromzero.zerobeginning.design_mode2.a1_strategy_demo_ordinary.AbstractHandler;

/**
 * @Desciption: 策略模式实现方式   InitializingBean（初始化bean的时候都会执行该接口的afterPropertiesSet方法）
 *              比 @Bean (initMethod = "方法")初始化方法执行更早
 * @Auther: ZhangXueCheng4441
 * @Date:2020/12/12/012 15:11
 */
public abstract class Strategy4AbstractHandler extends AbstractHandler {

    public abstract void method1();

    public abstract void method2();

    public abstract void method3();

    public abstract void method4();

    public abstract void method5();

    public abstract void method6();
}
