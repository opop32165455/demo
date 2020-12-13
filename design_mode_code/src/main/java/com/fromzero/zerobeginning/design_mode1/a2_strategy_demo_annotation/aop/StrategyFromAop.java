package com.fromzero.zerobeginning.design_mode1.a2_strategy_demo_annotation.aop;

import com.fromzero.zerobeginning.entity.GuiHuaFu;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @Desciption:
 * @Auther: ZhangXueCheng4441
 * @Date:2020/12/12/012 20:59
 */
@Component
@Slf4j
@Aspect
public class StrategyFromAop {

    @Pointcut(value = "@annotation(com.fromzero.zerobeginning.design_mode1.a2_strategy_demo_annotation.StrategyAnno)")
    public void  pointcut1(){
    }
    @Pointcut(value = "execution(public * com.fromzero.zerobeginning.controller.SysUserController.selectAll())")
    public void  pointcut2(){
    }
    @Before("pointcut2()")
    public void before(){
        System.out.println(GuiHuaFu.横线+"before");
    }
    @After("pointcut2()")
    public void after(){
        System.out.println(GuiHuaFu.横线+"after");
    }

    @Around(value = "@annotation(com.fromzero.zerobeginning.design_mode1.a2_strategy_demo_annotation.StrategyAnno)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Around 之前");
        System.out.println("Around 之前");
        Object result = joinPoint.proceed();
        log.info("Around 之后");
        System.out.println("Around 之后");
        return result;
    }

}
