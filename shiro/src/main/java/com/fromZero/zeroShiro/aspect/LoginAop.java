package com.fromZero.zeroShiro.aspect;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author zhangxuecheng4441
 * @date 2021/1/6/006 17:38
 */
@Component
@Aspect
public class LoginAop {
    private static final Logger LOG = LoggerFactory.getLogger(LoginAop.class);

    @Pointcut("execution (* com.fromZero.zeroShiro.controller.*.*(..) ) || execution (* com.fromZero.zeroShiro.controller.SysUserController.*.*(..) )")
    public void controller() {
    }

    @Around("controller()")
    public Object shiroTryCatch(ProceedingJoinPoint joinPoint) {
        Object result = "afterLogin/index.html";
        try {
            result = joinPoint.proceed();
        } catch (UnknownAccountException e) {
            LOG.error("用户名不存在！", e);
            result = "beforeLogin/error.html";
        } catch (IncorrectCredentialsException e) {
            LOG.error("认证错误！", e);
            //new RolesAuthorizationFilter()
            result = "beforeLogin/error.html";
        } catch (LockedAccountException e) {
            LOG.error("账户锁定！", e);
            result = "beforeLogin/error.html";
        } catch (UnauthorizedException e) {
            LOG.error("没有权限访问该地址！", e);
            result = "beforeLogin/error.html";
        } catch (AuthenticationException e) {
            LOG.error("身份验证错误！", e);
            result = "beforeLogin/error.html";
        } catch (AuthorizationException e) {
            LOG.error("没有权限！", e);
            result = "beforeLogin/error.html";
        } catch (Exception e) {
            e.printStackTrace();
            result = "beforeLogin/error.html";
        } finally {
            return result;
        }
    }
}
