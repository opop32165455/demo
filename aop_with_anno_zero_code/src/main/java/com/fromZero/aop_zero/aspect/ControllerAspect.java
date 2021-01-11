package com.fromZero.aop_zero.aspect;


import com.fromZero.aop_zero.annotation.ControllerTryCatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * @author R4441
 * @author zhangxuecheng4441
 * @date 2021/1/9/009 16:55
 */
@Component
@Aspect
/**优先级（越小越优先）*/
@Order(1)
public class ControllerAspect {
    private static final Logger LOG = LoggerFactory.getLogger(ControllerAspect.class);

    /**
     * 针对注解
     */
    @Pointcut(value = "@annotation(com.fromZero.aop_zero.annotation.ControllerTryCatch)")
    public void annotationPointcut() {
    }

    /**
     * 针对方法
     */
    @Pointcut(value = "execution(* com.fromZero.aop_zero.controller.SysUserController.insert(..))" +
            "||execution(* com.fromZero.aop_zero.controller.SysUserController.selectAll(..))")
    public void methodPointcut() {
    }

    /**
     * 针对整个类
     */
    @Pointcut(value = "execution(* com.fromZero.aop_zero.controller.SysUserController.insert(..))" +
            "||execution(* com.fromZero.aop_zero.controller.SysUserController.*(..))")
    public void classPointcut() {
    }

    /**
     * 针对整个包
     */
    @Pointcut(value = "execution(* com.fromZero.aop_zero.controller.SysUserController.insert(..))" +
            "||execution(* com.fromZero.aop_zero.controller.*.*(..))")
    public void packagePointcut() {
    }

    /**
     * 前置通知
     */
    @Before("methodPointcut()")
    public void before() {
        LOG.info("before");
    }

    /**
     * 方法后置通知（最终通知）
     */
    @After("classPointcut()")
    public void after() {
        System.out.println("after");
        LOG.info("before");
    }

    /**
     * 方法返回值之后（异常不通知）
     */
    @AfterReturning("methodPointcut()")
    public void afterReturning() {
        System.out.println("afterReturning");
        LOG.info("before");
    }

    /**
     * 异常通知
     */
    @AfterThrowing("methodPointcut()")
    public void afterThrowing() {
        System.out.println("afterThrowing");
        LOG.info("before");
    }

    @Around(value = "annotationPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        // 返回目标对象，即被代理的对象
        Object target = joinPoint.getTarget();
        //返回切入点的参数
        Object[] args = joinPoint.getArgs();
        //返回切入的类型，比如method-call，field-get，method-execution等
        String kind = joinPoint.getKind();
        //代理对象
        Object aThis = joinPoint.getThis();
        //可以发挥反射的功能获取关于类的任何信息，例如获取类名如下
        String className = joinPoint.getTarget().getClass().getName();
        //方法名
        String methodName = joinPoint.getSignature().getName();

        //方法上更多信息 MethodSignature只是其中一个实现类 根据aop的位置 可以选择类/元素等各种
        MethodSignature methodSignature = (MethodSignature) signature;
        //方法参数名
        String[] parameterNames = methodSignature.getParameterNames();
        //方法参数类型
        Class[] parameterTypes = methodSignature.getParameterTypes();
        Method method = methodSignature.getMethod();
        Optional.ofNullable(method).ifPresent(annotationClass -> {
            ControllerTryCatch annotation = method.getAnnotation(ControllerTryCatch.class);
            String controllerMethodName = annotation.ControllerMethodName();
            System.out.println("controllerMethodName = " + controllerMethodName);
        });

        //获取原始对象
        Class<?> targetClass1 = AopUtils.getTargetClass(joinPoint.getTarget());

        Class<?> targetClass2 = AopUtils.getTargetClass(signature);
        LOG.info("Around 之前");
        Object result = joinPoint.proceed();
        LOG.info("Around 之后");
        return result;
    }
}
