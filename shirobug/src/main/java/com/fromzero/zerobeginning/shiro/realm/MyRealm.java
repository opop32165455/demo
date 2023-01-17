package com.fromzero.zerobeginning.shiro.realm;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fromzero.zerobeginning.entity.SysUser;
import com.fromzero.zerobeginning.service.SysUserService;
import com.fromzero.zerobeginning.shiro.domain.DatawShiroToken;
import com.fromzero.zerobeginning.shiro.domain.MyAuthenticationInfo;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * @Desciption:
 * @Auther: ZhangXueCheng4441
 * @Date:2020/12/2/002 20:48
 */
public class MyRealm extends AuthorizingRealm {

    @Resource
    private SysUserService sysUserService;

    /**
     * @MethodName doGetAuthorizationInfo
     * @Description 权限配置类
     * @Param [principalCollection]
     * @Return AuthorizationInfo
     * @Author WangShiLin
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取登录用户名
        String name = (String) principalCollection.getPrimaryPrincipal();
        //查询用户名称
        //SQL
        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        //sql 获取用户所对应的角色

        //添加角色
        //simpleAuthorizationInfo.addRole(role.getName());

        //sql 获取角色对应的权限
        //添加权限
        //for (SysPermission permissions : role.getPermissions()) {
        //    simpleAuthorizationInfo.addStringPermission(permissions.getName());
        //}

        return null;
    }

    /**
     * @param authenticationToken 登入用户信息
     * @return 鉴定信息 到match校验密码
     * @throws AuthenticationException 登录异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        //接受到shiro管理的用户tokon
         DatawShiroToken loginToken = (DatawShiroToken) authenticationToken;
        //DatawShiroToken loginToken = null;
        if (StringUtils.isEmpty(authenticationToken.getPrincipal())) {
            return null;
        }
        //获取用户信息
        // String password = loginToken.getStringPassword();
        Object principal = authenticationToken.getPrincipal();
        LambdaQueryWrapper<SysUser> lambdaWrapper = new QueryWrapper<SysUser>().lambda();
        lambdaWrapper.eq(SysUser::getEmail, loginToken.getUsername());
        SysUser dateBaseUserInfo = sysUserService.getOne(lambdaWrapper);
        if (dateBaseUserInfo == null) {
            //这里返回后会报出对应异常
            return null;
        } else {
            //这里验证authenticationToken和simpleAuthenticationInfo的信息
            SimpleAuthenticationInfo simpleAuthenticationInfo = new MyAuthenticationInfo(dateBaseUserInfo, "password", getName());
            return simpleAuthenticationInfo;
        }
    }
}