package com.fromZero.zeroShiro.shiro.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;

import java.util.Set;

/**
 * @author zhangxuecheng4441
 * @date 2020/12/29/029 20:58
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyAuthorizationInfo implements AuthorizationInfo {

    private static final long serialVersionUID = 3348156196120750094L;

    protected Set<String> roles;

    protected Set<String> stringPermissions;

    protected Set<Permission> objectPermissions;

    private String infoCustomAttribute1;

    private String infoCustomAttribute2;

}
