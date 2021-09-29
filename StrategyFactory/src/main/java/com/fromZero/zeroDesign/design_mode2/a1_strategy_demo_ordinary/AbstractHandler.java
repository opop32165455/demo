package com.fromZero.zeroDesign.design_mode2.a1_strategy_demo_ordinary;

import org.springframework.beans.factory.InitializingBean;

/**
 * @Desciption: 模板设计方法
 * @Auther: ZhangXueCheng4441
 * @Date:2020/12/12/012 16:32
 */
public abstract class AbstractHandler implements InitializingBean {
    public abstract void method1();

    public abstract void method2();
    //抽象方法 子类必须重写 比较面条必须得揉面呀
    public abstract void method3();

    public abstract void method4();

    //可以写上你特有的方法哟
    public void method5() {
        throw new UnsupportedOperationException();
    }

    //子类可以选择重写 也可以选择不重写 不重写就调用我给你写到的异常（不支持操作）
    public void method6() {
        throw new UnsupportedOperationException();
    }

    //就是为了在多态的时候 我可以正常调用方法1-8
    public void method7() {
        throw new UnsupportedOperationException();
    }

    public void method8() {
        throw new UnsupportedOperationException();
    }
}
