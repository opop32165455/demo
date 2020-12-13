package com.fromzero.zerobeginning.design_mode1.a1_strategy_demo_ordinary.strategy_2.impl;

import com.fromzero.zerobeginning.design_mode1.a1_strategy_demo_ordinary.strategy_2.Strategy2Handler;
import com.fromzero.zerobeginning.design_mode1.b_factory_mode.Factory1;
import com.fromzero.zerobeginning.entity.GuiHuaFu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Desciption: 类似于servic的写法
 * @Auther: ZhangXueCheng4441
 * @Date:2020/12/12/012 15:16
 */
@Component
public class Strategy2_demo implements Strategy2Handler {

    private static Logger LOG = LoggerFactory.getLogger(Strategy2_demo.class);

    @Override
    public void method1() {
        LOG.info(GuiHuaFu.横线 +"策略方法2：AAAAAAAAAA");
    }

    @Override
    public void method2() {
        LOG.info(GuiHuaFu.横线 +"策略方法2：BBBBBBBBBB");
    }

    @Override
    public void method3() {
        LOG.info(GuiHuaFu.横线 +"策略方法2：CCCCCCCCCCC");
    }

    @Override
    public void method4() {
        LOG.info(GuiHuaFu.横线 +"策略方法2：DDDDDDDDDDD");
    }
    /**
     * 把此方法注册到工厂中去
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Factory1.register("strategy2",this);
    }
}
