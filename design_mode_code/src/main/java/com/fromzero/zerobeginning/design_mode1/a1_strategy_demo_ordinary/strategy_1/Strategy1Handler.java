package com.fromzero.zerobeginning.design_mode1.a1_strategy_demo_ordinary.strategy_1;

import com.fromzero.zerobeginning.design_mode1.a1_strategy_demo_ordinary.Handler;

/**
 * @Desciption: 策略模式实现方式   InitializingBean（初始化bean的时候都会执行该接口的afterPropertiesSet方法）
 *              比 @Bean (initMethod = "方法")初始化方法执行更早
 * @Auther: ZhangXueCheng4441
 * @Date:2020/12/12/012 15:11
 */
public interface Strategy1Handler extends Handler {
   //可以写上你特有的方法哟 但是工厂那边会无法调用
}
