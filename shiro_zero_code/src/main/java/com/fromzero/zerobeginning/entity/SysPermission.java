package com.fromzero.zerobeginning.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * (SysPermission)表实体类
 *
 * @author makejava
 * @since 2020-12-23 22:05:34
 */
@SuppressWarnings("serial")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_sys_permission")
public class SysPermission extends Model<SysPermission> {
    private Integer id;
    /**
     * 权限名
     */
    private String name;
    /**
     * 该权限允许访问的url
     */
    private String url;


    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}