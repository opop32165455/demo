package com.fromZero.zero.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fromZero.zero.entity.SysPermission;

import java.util.List;

/**
 * (SysPermission)表数据库访问层
 *
 * @author makejava
 * @since 2020-12-23 22:04:41
 */
public interface SysPermissionDao extends BaseMapper<SysPermission> {
    /**
     * 通过权限id 查找该权限有哪些角色可以使用
     *
     * @return 角色名
     */
    List<String> selectRoleByPermissionId(Integer id);
}