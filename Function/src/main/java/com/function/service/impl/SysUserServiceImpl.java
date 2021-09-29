package com.function.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.function.dao.SysUserDao;
import com.function.entity.SysUser;
import com.function.service.SysUserService;
import org.springframework.stereotype.Service;

/**
 * (SysUser)表服务实现类
 *
 * @author makejava
 * @since 2020-12-12 14:28:36
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUser> implements SysUserService {

}