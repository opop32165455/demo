package com.fromZero.zero;


import com.fromZero.zeroDesign.DesignModeApplication;
import com.fromZero.zeroDesign.design_mode1.a1_strategy_demo_ordinary.Handler;
import com.fromZero.zeroDesign.design_mode1.b_factory_mode.Factory1;
import com.fromZero.zeroDesign.design_mode2.a1_strategy_demo_ordinary.AbstractHandler;
import com.fromZero.zeroDesign.design_mode2.b_factory_mode.Factory2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;

@SpringBootTest(classes = DesignModeApplication.class)
@RunWith(SpringRunner.class)
public class DesignModeApplicationTests {

    @Test
    public void contextLoads() {
        System.out.println("1");
    }

    /**
     * 测试简单的策略方法+简单的工厂模式
     * 适合场景：
     *        出现大量冗余的代码 可以抽取成策略 （其实就是简单的抽取方法）
     *        增加工厂模式 方便在大量的方法中 准确找出你需要的方法群
     * 举个栗子：
     *        现在有 处理面条的流程 过程及其复杂  要做一万碗面条（策略模式抽取 每次调用策略）
     *        现在有二十种不同的面条 每个操作都不一样 要每种做一万碗（工厂模式选择面条 做面方法放在策略模式）
     */
    @Test
    public void doStrategy123(){
        try {
            Handler handler = Factory1.getInvokeStategy("strategy1");//选择面条种类
            handler.method1();//揉面
            handler.method2();//做面条
            handler.method3();//下面/doge
        } catch (Exception e) {
            e.printStackTrace();//很容易空指针
        }
    }
    //通过注解的方式 注册的策略
    @Test
    public void doStrategy3(){
        try {
            Handler handler = Factory1.getInvokeStategy("strategy3");//选择面条种类
            handler.method1();//揉面
            handler.method2();//做面条
            handler.method3();//下面/doge
            handler.method4();//辣椒
        } catch (Exception e) {
            e.printStackTrace();//很容易空指针
        }
    }
    @Test
    public void doStrategy4(){
        try {
            AbstractHandler handler = Factory2.getInvokeStategy("strategy4");//选择面条种类
            handler.method1();//揉面
            handler.method2();//做面条
            handler.method3();//下面/doge
            handler.method5();//辣椒
            handler.method6();//胡椒
            handler.method7();//盐
        } catch (Exception e) {
            e.printStackTrace();//很容易空指针
        }
    }

    @Resource
    private HashMap factoryHandlerMap;
    @Test
    public void doStrategy5InBeanMap(){
        try {
            AbstractHandler handler = (AbstractHandler) factoryHandlerMap.get("strategy5");//选择面条种类
            handler.method1();//揉面
            handler.method2();//做面条
            handler.method3();//下面/doge
            handler.method7();//胡椒
            handler.method8();//热油
            handler.method6();//辣椒
        } catch (Exception e) {
            e.printStackTrace();//很容易空指针
        }
    }


}
