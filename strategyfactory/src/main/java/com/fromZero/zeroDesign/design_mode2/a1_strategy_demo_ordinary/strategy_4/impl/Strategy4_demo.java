package com.fromZero.zeroDesign.design_mode2.a1_strategy_demo_ordinary.strategy_4.impl;


import com.fromZero.zeroDesign.design_mode2.a1_strategy_demo_ordinary.strategy_4.Strategy4AbstractHandler;
import com.fromZero.zeroDesign.design_mode2.b_factory_mode.Factory2;
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
public class Strategy4_demo extends Strategy4AbstractHandler {

    private static Logger LOG = LoggerFactory.getLogger(Strategy4_demo.class);

    @Override
    public void method1() {
        LOG.info(GuiHuaFu.横线 +"策略方法4：AAAAAAAAAA");
    }

    @Override
    public void method2() {
        LOG.info(GuiHuaFu.横线 +"策略方法4：BBBBBBBBBB");
    }

    @Override
    public void method3() {
        LOG.info(GuiHuaFu.横线 +"策略方法4：CCCCCCCCCC");
    }

    @Override
    public void method4() {
        LOG.info(GuiHuaFu.横线 +"策略方法4：DDDDDDDDDD");
    }

    @Override
    public void method5() {
        LOG.info(GuiHuaFu.横线 +"策略方法4：EEEEEEEEEEE");
    }

    @Override
    public void method6() {
        LOG.info(GuiHuaFu.横线 +"策略方法4：FFFFFFFFFFF");
    }

    /**
     * 把此方法注册到工厂中去
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Factory2.register("strategy4",this);
    }
}
