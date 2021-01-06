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
        Object result = "login success !";
        try {
            result = joinPoint.proceed();
        } catch (UnknownAccountException e) {
            LOG.error("用户名不存在！", e);
            result = "用户名不存在！";
        } catch (IncorrectCredentialsException e) {
            LOG.error("认证错误！", e);
            result = "认证错误！";
        } catch (LockedAccountException e) {
            LOG.error("账户锁定！", e);
            result = "账户锁定！";
        } catch (UnauthorizedException e) {
            LOG.error("没有权限访问该地址！", e);
            result = "没有权限访问该地址！";
        }catch (AuthenticationException e){
            LOG.error("身份验证错误！", e);
            result = "身份验证错误！";
        }catch (AuthorizationException e) {
            LOG.error("没有权限！", e);
            result = "没有权限";
        } catch (Exception e) {
            e.printStackTrace();
            result = "未知错误";
        } finally {
            return result;
        }
    }
}
