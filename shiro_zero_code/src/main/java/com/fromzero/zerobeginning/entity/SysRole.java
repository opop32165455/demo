package com.fromzero.zerobeginning.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


/**
 * (SysRole)表实体类
 *
 * @author zxc4441
 * @since 2020-12-23 21:59:55
 */
@SuppressWarnings("serial")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_sys_role")
public class SysRole extends Model<SysRole> {
    private Integer id;
    /**
     * 角色名
     */
    private String name;
    /**
     * 创建时间
     */
    private Date createTime;


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