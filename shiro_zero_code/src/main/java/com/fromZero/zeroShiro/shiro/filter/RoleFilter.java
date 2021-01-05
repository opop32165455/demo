package com.fromZero.zeroShiro.shiro.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 *  role和permission校验是重复的 选择一个就行 最合适的是role
 *  权限数据以及在导入角色设置过了
 *
 * @author R4441
 * @Desciption:
 * @Auther: ZhangXueCheng4441
 * @Date:2020/12/2/002 22:32
 */
public class RoleFilter extends AuthorizationFilter {
    /**
     * 对请求进行权限过滤
     *
     * @param request http请求
     * @param response http响应
     * @param mappedValue url需要的权限信息
     * @return 默认返回false
     * @throws Exception {@link Exception}
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        Subject subject = getSubject(request, response);
        //获取该url 允许通过的角色信息
        String[] rolesArray = (String[]) mappedValue;
        if (rolesArray == null || rolesArray.length == 0) {
            return true;
        }
        //查询用户
        for (String s : rolesArray) {
            //跳转realm 查询用户权限
            if (subject.hasRole(s)) {
                return true;
            }
        }
        return false;
    }
}
