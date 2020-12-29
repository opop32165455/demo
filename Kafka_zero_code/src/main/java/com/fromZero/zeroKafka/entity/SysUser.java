package com.fromZero.zeroKafka.entity;

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

    private String name;

    private String email;

    private String country;

    private String password;

    private Integer groupId;

    private Date createTime;

    private Integer createUid;

    private Date lastLoginTime;

    private String lastLoginIp;

    private Integer status;

    private String timeZone;

    private Integer lang;

    private Integer isSys;

    private Integer isOpen;

    private Date validTime;

    private Integer isUkey;

    private String ukeyId;

    private String limitIp;


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