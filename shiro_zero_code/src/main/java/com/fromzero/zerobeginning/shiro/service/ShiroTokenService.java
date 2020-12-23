package com.fromzero.zerobeginning.shiro.service;


import org.apache.shiro.session.Session;

/**
 * @author R4441
 * @Desciption:
 * @Auther: ZhangXueCheng4441
 * @Date:2020/12/3/003 14:01
 */
public interface ShiroTokenService {
    /**
     * 获取token
     * @return 获取当前用户信息
     */
    SysUser getToken();

    /**
     * 获取当前shiro session
     * @return 当前shiro session
     */
    Session getCurrentSession();

    /**
     * 核心登陆操作
     * @param user 用户信息
     * @param remenberMe 是否记住密码
     * @return 用户信息
     */
    SysUser login(SysUser user, Boolean remenberMe);

    /**
     * 判断是否登陆
     * @return 是否登陆
     */
    boolean isLogin();

    /**
     * 登出
     */
    void logout();
}
