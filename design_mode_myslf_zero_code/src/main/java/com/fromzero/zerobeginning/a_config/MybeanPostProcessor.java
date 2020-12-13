package com.fromzero.zerobeginning.a_config;

import com.fromzero.zerobeginning.design_mode1.a1_strategy_demo_ordinary.Handler;
import com.fromzero.zerobeginning.design_mode1.a2_strategy_demo_annotation.StrategyAnno;
import com.fromzero.zerobeginning.design_mode1.b_factory_mode.Factory1;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;

/**
 * @Desciption: bean初始化 通过注解 把对应业务逻辑的策略 根据value 注册到工厂里去
 * @Auther: ZhangXueCheng4441
 * @Date:2020/12/13/013 10:27
 */
@Configuration
public class MybeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        StrategyAnno annotation = bean.getClass().getAnnotation(StrategyAnno.class);
        if (annotation != null && bean instanceof Handler){
            Factory1.register(annotation.value(), (Handler) bean);
        }
        return bean;
    }
}
