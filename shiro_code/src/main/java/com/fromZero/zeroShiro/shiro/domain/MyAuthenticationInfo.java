package com.fromZero.zeroShiro.shiro.domain;

import com.fromZero.zeroShiro.entity.SysUser;
import lombok.*;
import org.apache.shiro.authc.SimpleAuthenticationInfo;

/**
 *  父类属性
 *  PrincipalCollection principals; getPrimaryPrincipal 可以获得
 *  Object credentials;
 *  ByteSource credentialsSalt;
 *
 * @Desciption: 自定义 封装的shiro框架使用的验证信息类
 * @author zxc
 * @since 2020/12/7
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MyAuthenticationInfo extends SimpleAuthenticationInfo {

    private static final long serialVersionUID = 812808092055322168L;

    private String infoCustomAttribute1;

    private String infoCustomAttribute2;

    public MyAuthenticationInfo(SysUser dateBaseUserInfo, String password, String name) {
        super(dateBaseUserInfo,password,name);
    }
}
