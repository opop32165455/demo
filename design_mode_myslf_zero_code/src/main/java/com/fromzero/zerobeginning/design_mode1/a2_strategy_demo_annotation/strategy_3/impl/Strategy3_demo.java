package com.fromzero.zerobeginning.design_mode1.a2_strategy_demo_annotation.strategy_3.impl;


import com.fromzero.zerobeginning.design_mode1.a2_strategy_demo_annotation.strategy_3.Strategy3Handler;
import com.fromzero.zerobeginning.design_mode1.b_factory_mode.Factory1;
import com.fromzero.zerobeginning.entity.GuiHuaFu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Desciption: 类似于servic的写法
 * @Auther: ZhangXueCheng4441
 * @Date:2020/12/12/012 15:16
 */

@Component
public class Strategy3_demo implements Strategy3Handler {

    private static Logger LOG = LoggerFactory.getLogger(Strategy3_demo.class);

    @Override
    public void method1() {
        LOG.info(GuiHuaFu.横线 +"策略方法3：AAAAAAAAAA");
    }

    @Override
    public void method2() {
        LOG.info(GuiHuaFu.横线 +"策略方法3：BBBBBBBBBB");
    }

    @Override
    public void method3() {
        LOG.info(GuiHuaFu.横线 +"策略方法3：CCCCCCCCCC");
    }

    @Override
    public void method4() {
        LOG.info(GuiHuaFu.横线 +"策略方法3：DDDDDDDDDD");
    }

    /**
     * 把此方法注册到工厂中去
     *  todo @autowire list<handler> list
     *  todo  @postconstruct 用初始化bean执行的注解 也可以实现
     * @throws Exception
     */
    @Autowired
    @PostConstruct
    public void afterPropertiesSet() throws Exception {
        Factory1.register("strategy1",this);
    }
}
