package com.fromzero.zerobeginning.service;

/**
 * @author zhangxuecheng4441
 * @date 2020/12/16/016 15:39
 */
public interface LoginService {

    /**
     * 登录用户
     *
     * @param loginUserInfo 登录用户信息
     * @param isRememberMe 是否记住密码
     * @return 是否登入成功
     * @throws Exception 异常
     */
    boolean login(SysUser loginUserInfo, Boolean isRememberMe) throws Exception;
}
