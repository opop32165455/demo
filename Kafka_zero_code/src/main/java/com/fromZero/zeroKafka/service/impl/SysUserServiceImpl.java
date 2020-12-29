package com.fromZero.zeroKafka.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fromZero.zeroKafka.dao.SysUserDao;
import com.fromZero.zeroKafka.entity.SysUser;
import com.fromZero.zeroKafka.service.SysUserService;
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