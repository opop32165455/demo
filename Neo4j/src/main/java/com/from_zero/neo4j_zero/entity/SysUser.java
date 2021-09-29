package com.from_zero.neo4j_zero.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (SysUser)表实体类
 *
 * @author makejava
 * @since 2020-12-12 14:28:33
 */
@SuppressWarnings("serial")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_sys_user")
public class SysUser extends Model<SysUser> {
    private Integer id;
    /**
     * 用户昵称
     */
    private String name;
    /**
     * 登陆的账号
     */
    private String email;
    private String password;
    /**
     * 用户组id 可以给用户分组
     */
    private Integer groupId;
    private Date createTime;
    /**
     * 最后一次登录时间 可以用来判断是否发放优惠券
     */
    private Date lastLoginTime;
    /**
     * 最后一次登录的id 可以判断异地登录
     */
    private String lastLoginIp;
    /**
     * 判断用户账号是否正常
     */
    private Integer status;
    /**
     * 用户语言
     */
    private String language;


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