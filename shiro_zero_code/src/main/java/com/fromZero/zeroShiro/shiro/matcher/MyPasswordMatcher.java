package com.fromZero.zeroShiro.shiro.matcher;

import com.fromZero.zeroShiro.entity.SysUser;
import com.fromZero.zeroShiro.shiro.domain.MyAuthenticationInfo;
import com.fromZero.zeroShiro.shiro.domain.MyShiroToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.springframework.stereotype.Component;


/**
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
     * @param authenticationToken 用户登录信息token（已经封装成 DatawShiroToken）
     * @param authenticationInfo  该用户名的user的验证信息 （已经封装成 DatawAuthenticationInfo）
     * @return 加密之后的密码验证
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        //类型转换异常 配置redis缓存 解决
        MyShiroToken tokon = (MyShiroToken) authenticationToken;
        //String loginUserPassword = String.valueOf(tokon.getPassword());
        String loginUserPassword = tokon.getStringPassword();
        boolean matches = false;
        MyAuthenticationInfo dataBaseInfo = (MyAuthenticationInfo) authenticationInfo;
        Object subject = SecurityUtils.getSubject();
        //PrincipalCollection getPrincipals中PrimaryPrincipal封裝了用户信息
        SysUser DataBaseUser = (SysUser) dataBaseInfo.getPrincipals().getPrimaryPrincipal();
        if (DataBaseUser.getPassword().equals(loginUserPassword)) {
            matches = true;
        }


        ////获取当前用户tokon信息
        //DatawShiroToken shiroToken = (DatawShiroToken)authenticationToken;
        ////获取当前用户验证信息
        //SysUser user = (SysUser) authenticationInfo.getPrincipals().getPrimaryPrincipal();
        ////根据密码，时间戳，随机数进行md5加密(将数据库密码+前端传来的时间戳+随机数 )
        //String rightCredentials = Md5Util.md5(user.getPassword() + shiroToken.getTimestamp() + shiroToken.getRand());
        ////验证密码是否正确 对比前端传来的credentials
        //boolean matches = rightCredentials.equals(shiroToken.getPasswd());

        return matches;
    }
}
