package com.fromzero.zerobeginning.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fromzero.zerobeginning.dao.SysPermissionDao;
import com.fromzero.zerobeginning.entity.SysPermission;
import com.fromzero.zerobeginning.service.SysPermissionService;
import org.springframework.stereotype.Service;


/**
 * (SysPermission)表服务实现类
 *
 * @author makejava
 * @since 2020-12-23 22:08:31
 */
@Service("sysPermissionService")
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionDao, SysPermission> implements SysPermissionService {

}