package com.fromzero.zerobeginning.shiro.filter;

import org.apache.shiro.web.servlet.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @Desciption: 每一次請求之前都会进行拦截
 * @Auther: ZhangXueCheng4441
 * @Date:2020/12/9/009 11:49
 */
public class AddPrincipalToSessionFilter extends OncePerRequestFilter {
    /**
     *  拦截请求 判断是否记住密码 如果记住密码 提供session (整合redis session之后 已经解决了session丢失问题)
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {

        ////查询当前用户的信息
        //Subject jdkobservable = SecurityUtils.getSubject();
        ////判断用户是不是通过自动登录进来的
        //if (jdkobservable.isRemembered()) {
        //    SysUser sysUser = null;
        //    //如果是，则获取它的用户信息
        //    if (jdkobservable != null && jdkobservable.getPrincipal() != null && jdkobservable.getPrincipal() instanceof SysUser) {
        //        sysUser = (SysUser) jdkobservable.getPrincipal();
        //    }
        //    //由于是继承的OncePerRequestFilter，没办法设置session
        //    //这里发现可以将servletReques强转为子类，所以使用request.getsiion())
        //    HttpServletRequest request = (HttpServletRequest) servletRequest;
        //    HttpSession session = request.getSession();
        //    //当session为空的时候
        //    if (session.getAttribute("user") == null) {
        //        //把查询到的用户信息设置为session，时效为30天
        //        session.setAttribute("user", sysUser);
        //        session.setMaxInactiveInterval(60*60*24*30);
        //    }
        //}
        filterChain.doFilter(servletRequest, servletResponse);
    }

}

