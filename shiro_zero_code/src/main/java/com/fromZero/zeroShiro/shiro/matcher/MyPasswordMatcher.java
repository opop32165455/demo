package com.fromZero.zeroShiro.shiro.matcher;

import com.fromZero.zeroShiro.entity.SysUser;
import com.fromZero.zeroShiro.shiro.domain.MyAuthenticationInfo;
import com.fromZero.zeroShiro.shiro.domain.MyShiroToken;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.springframework.stereotype.Component;


/**
 *
 * @author zxc
 * @Desciption: 密码校验类  （需要配置交给shiro）
 * @Auther: ZhangXueCheng4441
 * @Date: 2020/12/4/20:44
 */
@Component
public class MyPasswordMatcher implements CredentialsMatcher {
    /**
     * 重写shiro密码校验
     *
     * @param authenticationToken 用户登录信息token（已经封装成 MyShiroToken）
     * @param authenticationInfo  该用户名的user的验证信息 （已经封装成 MyAuthenticationInfo）
     * @return 加密之后的密码验证
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        boolean matches = false;
        //类型转换异常 配置redis缓存 解决
        //登陆的用户信息 token
        MyShiroToken token = (MyShiroToken) authenticationToken;
        String loginUserPassword = token.getStringPassword();
        //数据库用户信息 token
        MyAuthenticationInfo dataBaseInfo = (MyAuthenticationInfo) authenticationInfo;
        SysUser dataBaseUser = (SysUser) dataBaseInfo.getPrincipals().getPrimaryPrincipal();
        //对比两个密码
        if (dataBaseUser.getPassword().equals(loginUserPassword)) {
            matches = true;
        }
        return matches;
    }
}
