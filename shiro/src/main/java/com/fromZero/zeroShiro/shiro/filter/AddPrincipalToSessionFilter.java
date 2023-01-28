package com.fromZero.zeroShiro.shiro.filter;

import org.apache.shiro.web.servlet.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * 特殊业务 很适合使用 等于一个超大范围aop
 *
 * @author R4441
 * @Desciption: 每一次請求之前都会进行拦截
 * @Auther: ZhangXueCheng4441
 * @Date:2020/12/9/009 11:49
 */
public class AddPrincipalToSessionFilter extends OncePerRequestFilter {
    /**
     *  拦截请求 判断是否记住密码 如果记住密码 提供session (整合redis session之后 已经解决了session丢失问题)
     * @param servletRequest {@link ServletRequest} 请求
     * @param servletResponse {@link ServletResponse} 响应
     * @param filterChain  {@link FilterChain} 权限过滤链
     * @throws ServletException {@link ServletException}
     * @throws IOException {@link IOException}
     */
    @Override
    protected void doFilterInternal(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        filterChain.doFilter(servletRequest, servletResponse);
    }

}

