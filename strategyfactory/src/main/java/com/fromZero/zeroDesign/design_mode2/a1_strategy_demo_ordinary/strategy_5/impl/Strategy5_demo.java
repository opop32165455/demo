package com.fromZero.zeroDesign.design_mode2.a1_strategy_demo_ordinary.strategy_5.impl;

import com.fromZero.zeroDesign.design_mode2.a1_strategy_demo_ordinary.strategy_5.Strategy5AbstractHandler;
import com.fromZero.zeroDesign.entity.GuiHuaFu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @Desciption: 类似于servic的写法
 * @Auther: ZhangXueCheng4441
 * @Date:2020/12/12/012 15:16
 */
@Component
public class Strategy5_demo extends Strategy5AbstractHandler {

    private static Logger LOG = LoggerFactory.getLogger(Strategy5_demo.class);
    @Resource(name = "factoryHandlerMap")
    private HashMap factoryHandlerMap;

    @Override
    public void method1() {
        LOG.info(GuiHuaFu.横线 +"策略方法5：AAAAAAAAAA");
    }

    @Override
    public void method2() {
        LOG.info(GuiHuaFu.横线 +"策略方法5：BBBBBBBBBB");
    }

    @Override
    public void method3() {
        LOG.info(GuiHuaFu.横线 +"策略方法5：CCCCCCCCCCC");
    }

    @Override
    public void method4() {
        LOG.info(GuiHuaFu.横线 +"策略方法5：DDDDDDDDDDD");
    }

    @Override
    public void method7() {
        LOG.info(GuiHuaFu.横线 +"策略方法5：EEEEEEEEEEE");
    }

    @Override
    public void method8() {
        LOG.info(GuiHuaFu.横线 +"策略方法5：FFFFFFFFFFF");
    }

    /**
     * 把此方法注册到工厂中去
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
       // Factory2.register("strategy5",this);
        factoryHandlerMap.put("strategy5",this);
    }
}
