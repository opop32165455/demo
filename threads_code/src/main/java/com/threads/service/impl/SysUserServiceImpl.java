package com.threads.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.threads.dao.SysUserDao;
import com.threads.entity.SysUser;
import com.threads.service.SysUserService;
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