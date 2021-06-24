package com.fromzero.zerobeginning.shiro.domain;

import com.fromzero.zerobeginning.entity.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.shiro.authc.SimpleAuthenticationInfo;

/**
 *  父类属性
 *  PrincipalCollection principals; getPrimaryPrincipal 可以获得
 *  Object credentials;
 *  ByteSource credentialsSalt;
 *
 * @Desciption: 自定义 封装的shiro框架使用的验证信息类
 * @author lin
 * @since 2017/4/7.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyAuthenticationInfo extends SimpleAuthenticationInfo {

    private String infoCustomAttribute1;

    private String infoCustomAttribute2;

    public MyAuthenticationInfo(SysUser dateBaseUserInfo, String password, String name) {
        super(dateBaseUserInfo,password,name);
    }
}
