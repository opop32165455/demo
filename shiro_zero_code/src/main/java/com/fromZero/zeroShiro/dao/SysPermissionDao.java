package com.fromZero.zeroShiro.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fromZero.zeroShiro.entity.SysPermission;
import org.apache.ibatis.annotations.Param;

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
     * @param id 权限id
     * @return 角色名
     */
    List<String> selectRoleByPermissionId(Integer id);

    /**
     * 根据角色id 查询权限信息
     *
     * @param id 角色id
     * @return 权限信息
     */
    List<SysPermission> selectByRoleId(@Param("rid") Integer id);
}