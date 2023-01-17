package com.fromZero.zeroShiro.shiro.filter;


import com.fromZero.zeroShiro.shiro.domain.MyShiroToken;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 如果没有整合redis session 需要手动重写shiro的token
 * 如果整合了redis 自定义的token可以直接放入redis中 不需要使用过滤
 *
 * @author ZhangXueCheng4441
 * @Desciption: 重写shiro token
 * @Date:2020/12/4 20:44
 */
public class MyFormFilter extends FormAuthenticationFilter {
    /**
     * 重写shiro的AuthenticationToken 包装获得想要获得的用户数据 （需要在配置filter工厂里面添加过滤器）
     *
     * @param request  http请求
     * @param response http响应
     * @return 新的token
     */
    @Override
    protected MyShiroToken createToken(ServletRequest request, ServletResponse response) {
        String username = getUsername(request);
        String password = getPassword(request);
        String host = getHost(request);
        String isRememberMe = getRememberMeParam();
        //可以往token里面添加想要加入的数据
        return new MyShiroToken(username, password, Boolean.getBoolean(isRememberMe), host, "TokenFilter1", "TokenFilter2");
    }


}
