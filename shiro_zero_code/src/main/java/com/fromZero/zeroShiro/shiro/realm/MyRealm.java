package com.fromZero.zeroShiro.shiro.realm;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fromZero.zeroShiro.dao.SysUserDao;
import com.fromZero.zeroShiro.entity.SysPermission;
import com.fromZero.zeroShiro.entity.SysRole;
import com.fromZero.zeroShiro.entity.SysUser;
import com.fromZero.zeroShiro.service.SysUserService;
import com.fromZero.zeroShiro.shiro.domain.MyAuthenticationInfo;
import com.fromZero.zeroShiro.shiro.domain.MyAuthorizationInfo;
import com.fromZero.zeroShiro.shiro.domain.MyShiroToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zxc
 * @Desciption: shiro核心登录鉴权类(与数据交互层）
 * @Auther: ZhangXueCheng4441
 * @Date:2020/12/2/002 20:48
 */
public class MyRealm extends AuthorizingRealm {

    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysUserDao sysUserDao;

    /**
     * 权限配置类
     *
     * @param principalCollection 登陆者信息
     * @return AuthorizationInfo 权限信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取登录用户 todo
        // MyPrincipalCollection myPrincipalCollection =  (MyPrincipalCollection) principalCollection;
        // String userEmail = (String) myPrincipalCollection.getPrimaryPrincipal();
        SysUser user = (SysUser) principalCollection.getPrimaryPrincipal();

        //添加角色和权限
        MyAuthorizationInfo myAuthorizationInfo = new MyAuthorizationInfo();
        Set<String> roles = new LinkedHashSet<>();
        //查询该用户信息
        SysUser sysUser = sysUserDao.selectUserPermissionsByEmail(user.getEmail());
        Set<String> permissions = sysUser.getRoles()
                .stream()
                //添加角色信息
                .peek(role -> roles.add(role.getName()))
                .map(SysRole::getPermissions)
                //添加权限信息
                .flatMap(a -> a.stream().map(SysPermission::getName))
                .collect(Collectors.toSet());

        myAuthorizationInfo.setRoles(roles);
        //myAuthorizationInfo.setStringPermissions(permissions);
        return myAuthorizationInfo;
    }

    /**
     * @param authenticationToken 登入用户信息
     * @return 鉴定信息 到match校验密码
     * @throws AuthenticationException 登录异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        //接受到shiro管理的用户token （配置redis 解决类型转换异常问题）
        MyShiroToken loginToken = (MyShiroToken) authenticationToken;
        if (StringUtils.isEmpty(authenticationToken.getPrincipal())) {
            return null;
        }
        //获取用户信息
        // String password = loginToken.getStringPassword();
        Object principal = authenticationToken.getPrincipal();
        LambdaQueryWrapper<SysUser> lambdaWrapper = new QueryWrapper<SysUser>()
                .lambda()
                .eq(SysUser::getEmail, loginToken.getUsername());
        SysUser dateBaseUserInfo = sysUserService.getOne(lambdaWrapper);
        if (dateBaseUserInfo == null) {
            //这里返回后会报出对应异常
            return null;
        } else {
            //这里验证弹去match 校验密码 这里把该用户真实密码封装到info
            //想怎么封装怎么封装 看业务需要
            return new MyAuthenticationInfo(dateBaseUserInfo, "password", getName());
        }
    }
}