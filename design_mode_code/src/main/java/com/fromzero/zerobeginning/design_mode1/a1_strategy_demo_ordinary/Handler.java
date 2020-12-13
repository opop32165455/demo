package com.fromzero.zerobeginning.design_mode1.a1_strategy_demo_ordinary;

import org.springframework.beans.factory.InitializingBean;

/**
 * @Desciption:
 * @Auther: ZhangXueCheng4441
 * @Date:2020/12/12/012 16:32
 */
public interface Handler extends InitializingBean {
    void method1();
    void method2();
    void method3();
    void method4();
}
