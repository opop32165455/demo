package com.fromzero.zerobeginning.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fromzero.zerobeginning.dao.SysUserDao;
import com.fromzero.zerobeginning.entity.SysUser;
import com.fromzero.zerobeginning.service.SysUserService;
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