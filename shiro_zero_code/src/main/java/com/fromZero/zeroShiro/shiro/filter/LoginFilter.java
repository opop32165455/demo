package com.fromZero.zeroShiro.shiro.filter;


import com.fromZero.zeroShiro.consts.AuthResponse;
import com.fromZero.zeroShiro.entity.SysUser;
import com.fromZero.zeroShiro.utils.ShiroWebUtil;
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
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     * @throws Exception
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        Subject token = getSubject(request,response);
        SysUser sysUser = (SysUser) token.getPrincipal();
        //判断是否登陆
        if(null != sysUser || isLoginRequest(request, response)){
            return Boolean.TRUE;
        }
        //判断是不是ajax请求，是则返回json
        if(ShiroWebUtil.isAjax(request)){
            ShiroWebUtil.output(response, AuthResponse.NO_LOGIN);
        }
        return Boolean.FALSE ;
    }

    /**
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        this.setLoginUrl("/login.html");
        saveRequestAndRedirectToLogin(request, response);
        return Boolean.FALSE ;
    }



}
