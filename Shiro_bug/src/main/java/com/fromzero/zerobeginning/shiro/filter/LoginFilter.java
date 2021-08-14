package com.fromzero.zerobeginning.shiro.filter;


import com.fromzero.zerobeginning.consts.AuthResponse;
import com.fromzero.zerobeginning.entity.SysUser;
import com.fromzero.zerobeginning.utils.ShiroWebUtil;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author lin
 * @since 2017/4/5.
 */

public class LoginFilter extends AccessControlFilter {
    //拦截登录 检验是否登陆
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        Subject token = getSubject(request,response);
        SysUser sysUser = (SysUser) token.getPrincipal();
        if(null != sysUser || isLoginRequest(request, response)){// && isEnabled()
            return Boolean.TRUE;
        }
        //判断是不是ajax请求，是则返回json
        if(ShiroWebUtil.isAjax(request)){
            ShiroWebUtil.output(response, AuthResponse.NO_LOGIN);
        }
        return Boolean.FALSE ;
    }
    //发现没有登陆 重定向到登陆界面
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        this.setLoginUrl("/login.html");
        saveRequestAndRedirectToLogin(request, response);
        return Boolean.FALSE ;
    }



}
