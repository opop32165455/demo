package com.fromZero.zeroShiro.shiro.filter;


import com.fromZero.zeroShiro.entity.SysUser;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author R4441
 * @Desciption:
 * @Auther: ZhangXueCheng4441
 * @Date:2020/12/3  21:00
 */
public class LoginFilter extends AccessControlFilter {
    //拦截登录 检验是否登陆

    /**
     * 检验请求的用户是否登陆
     *
     * @param request http请求
     * @param response http响应
     * @param mappedValue url需要的权限信息
     * @return 默认返回false
     * @throws Exception {@link Exception}
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        Subject token = getSubject(request,response);
        SysUser sysUser = (SysUser) token.getPrincipal();
        //判断是否登陆
        if(null != sysUser || isLoginRequest(request, response)){
            return Boolean.TRUE;
        }
        return Boolean.FALSE ;
    }

    /**
     * 验证发现未登录
     * 转跳置login.html页面
     * 保存这次请求到session
     *
     * @param request http请求
     * @param response http响应
     * @return 默认返回false
     * @throws Exception {@link Exception}
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        this.setLoginUrl("/login.html");
        saveRequestAndRedirectToLogin(request, response);
        return Boolean.FALSE ;
    }



}
