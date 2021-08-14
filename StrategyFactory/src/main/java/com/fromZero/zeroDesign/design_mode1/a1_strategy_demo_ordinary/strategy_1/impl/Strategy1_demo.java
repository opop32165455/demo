package com.fromZero.zeroDesign.design_mode1.a1_strategy_demo_ordinary.strategy_1.impl;


import com.fromZero.zeroDesign.design_mode1.a1_strategy_demo_ordinary.strategy_1.Strategy1Handler;
import com.fromZero.zeroDesign.design_mode1.a2_strategy_demo_annotation.StrategyAnno;
import com.fromZero.zeroDesign.design_mode1.b_factory_mode.Factory1;
import com.fromZero.zeroDesign.entity.GuiHuaFu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Desciption: 类似于servic的写法
 * @Auther: ZhangXueCheng4441
 * @Date:2020/12/12/012 15:16
 */
@Component
public class Strategy1_demo implements Strategy1Handler {

    private static Logger LOG = LoggerFactory.getLogger(Strategy1_demo.class);

    @Override
    @StrategyAnno("abc")
    public void method1() {
        LOG.info(GuiHuaFu.横线 +"策略方法1：AAAAAAAAAA");
    }

    @Override
    public void method2() {
        LOG.info(GuiHuaFu.横线 +"策略方法1：BBBBBBBBBB");
    }

    @Override
    public void method3() {
        LOG.info(GuiHuaFu.横线 +"策略方法1：CCCCCCCCCC");
    }

    @Override
    public void method4() {
        LOG.info(GuiHuaFu.横线 +"策略方法1：DDDDDDDDDD");
    }

    /**
     * 把此方法注册到工厂中去
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Factory1.register("strategy1",this);
    }
}
