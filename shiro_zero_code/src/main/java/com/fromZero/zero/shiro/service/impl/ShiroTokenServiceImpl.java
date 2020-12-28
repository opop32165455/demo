package com.fromZero.zero.shiro.service.impl;


import com.fromZero.zero.entity.SysUser;
import com.fromZero.zero.shiro.domain.MyShiroToken;
import com.fromZero.zero.shiro.realm.MyRealm;
import com.fromZero.zero.shiro.service.ShiroTokenService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author R4441
 * @Desciption:  token 相关方法
 * @Auther:  ZhangXueCheng4441
 * @Date:2020/12/3/003 14:01
 */
@Service
public class ShiroTokenServiceImpl implements ShiroTokenService {

    @Resource
    private MyRealm myRealm;


    /**
     * 获取当前登录的用户User对象信息
     * 需要在realm返回值中加入user数据
     *
     * @return subject中储存的用户信息（
     */
    @Override
    public SysUser getToken() {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null && subject.getPrincipal() != null && subject.getPrincipal() instanceof SysUser) {
            return (SysUser) subject.getPrincipal();
        }
        return null;
    }

    /**
     * 获取用户session信息（shiro.session）
     *
     * @return 用户session信息
     */
    @Override
    public Session getCurrentSession() {
        return SecurityUtils.getSubject().getSession();
    }

    /**
     * 用户登陆
     *
     * @param user       用户信息
     * @param rememberMe 记住密码
     * @return 用户信息
     */
    @Override
    public SysUser login(SysUser user, Boolean rememberMe) {
        //构造一个登录使用的集中用户数据的tokon
        MyShiroToken shiroToken = new MyShiroToken(user.getEmail(), user.getPassword(),rememberMe,"host","token1","token2");
        shiroToken.setRememberMe(rememberMe);
        //使用token进行登陆
        SecurityUtils.getSubject().login(shiroToken);
        return getToken();
    }

    /**
     * 判断是否登陆
     *
     * @return 是否登陆
     */
    @Override
    public boolean isLogin() {
        boolean authenticated = SecurityUtils.getSubject().isAuthenticated();
        System.out.println("authenticated = " + authenticated);
        return SecurityUtils.getSubject().getPrincipal() != null;

    }

    /**
     * 登出
     */
    @Override
    public void logout() {
        //清除缓存
        //customRealm.doClearCache(SecurityUtils.getSubject().getPrincipals());
        SecurityUtils.getSubject().logout();
    }
}
