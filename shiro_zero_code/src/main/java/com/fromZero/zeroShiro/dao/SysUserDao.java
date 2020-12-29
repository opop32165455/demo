package com.fromZero.zeroShiro.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fromZero.zeroShiro.entity.SysUser;
import org.apache.ibatis.annotations.Param;

/**
 * (SysUser)表数据库访问层
 *
 * @author makejava
 * @since 2020-12-12 14:28:35
 */
public interface SysUserDao extends BaseMapper<SysUser> {
    /**
     * 根据用户登录邮箱 查询登录者所有权限
     *
     * @param email 用户登录邮箱
     * @return 用户权限信息
     */
   SysUser selectUserPermissionsByEmail(@Param(value = "email") String email);
}