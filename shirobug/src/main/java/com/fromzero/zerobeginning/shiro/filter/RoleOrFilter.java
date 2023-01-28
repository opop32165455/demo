package com.fromzero.zerobeginning.shiro.filter;

import com.fromzero.zerobeginning.consts.AuthResponse;
import com.fromzero.zerobeginning.utils.ShiroWebUtil;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 *  role和permission校验是重复的 选择一个就行 最合适的是role
 *  权限数据以及在导入角色设置过了
 *
 * 一个角色满足，即可访问
 * @author lin
 * @since 2017/4/7.
 */

public class RoleOrFilter extends AuthorizationFilter {
    //拦截到了 发现没有角色
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        Subject subject = getSubject(request, response);
        String[] rolesArray = (String[]) mappedValue;
        if (rolesArray == null || rolesArray.length == 0) {
            return true;
        }
        for (int i = 0; i < rolesArray.length; i++) {
            //跳转realm 查询用户权限
            if (subject.hasRole(rolesArray[i])) {
                return true;
            }
        }
        //判断是不是ajax请求，是则返回json
        if(ShiroWebUtil.isAjax(request)){
            ShiroWebUtil.output(response, AuthResponse.NO_AUTH);
        }
        return false;
    }
}
