package com.fromZero.zero.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.fromZero.zero.entity.SysUser;
import com.fromZero.zero.service.LoginService;
import com.fromZero.zero.shiro.service.ShiroTokenService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zhangxuecheng4441
 * @date 2020/12/16/016 15:42
 */
@Service("loginService")
public class LoginServiceImpl implements LoginService {

    @Resource
    private ShiroTokenService shiroTokenService;

    @Override
    public boolean login(SysUser LoginUserInfo, Boolean isRememberMe) throws Exception {
        //进行登陆
        SysUser loginUser = shiroTokenService.login(LoginUserInfo, isRememberMe);
        if (ObjectUtil.isNotNull(loginUser)) {
            return true;
        }
        return false;
    }
}
