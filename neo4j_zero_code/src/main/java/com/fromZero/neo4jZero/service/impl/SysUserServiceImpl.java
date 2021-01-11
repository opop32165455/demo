package com.fromZero.neo4jZero.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fromZero.neo4jZero.dao.SysUserDao;
import com.fromZero.neo4jZero.entity.SysUser;
import com.fromZero.neo4jZero.service.SysUserService;
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