package com.fromZero.zeroShiro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fromZero.zeroShiro.dao.SysRoleDao;
import com.fromZero.zeroShiro.entity.SysRole;
import com.fromZero.zeroShiro.service.SysRoleService;
import org.springframework.stereotype.Service;

/**
 * (SysRole)表服务实现类
 *
 * @author makejava
 * @since 2020-12-23 22:10:32
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRole> implements SysRoleService {

}