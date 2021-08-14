package com.fromzero.zerobeginning.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.fromzero.zerobeginning.entity.SysUser;
import com.fromzero.zerobeginning.service.LoginService;
import com.fromzero.zerobeginning.shiro.service.ShiroTokenService;
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

        SysUser loginUser = shiroTokenService.login(LoginUserInfo, isRememberMe);

        SysUser token = shiroTokenService.getToken();
        if (ObjectUtil.isNotNull(loginUser)) {
            return true;
        }
        return false;
    }
}
