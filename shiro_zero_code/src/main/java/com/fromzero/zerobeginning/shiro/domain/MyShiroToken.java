package com.fromzero.zerobeginning.shiro.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.shiro.authc.UsernamePasswordToken;

import java.io.Serializable;

/**
 *
 * 自定义 封装的shiro框架使用的token
 *
 * * 父类属性
 * * @param private String username;
 * * @param private char[] password;
 * * @param  private boolean rememberMe;
 * * @param  private String host;
 * @author lin
 * @since 2017/4/7.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyShiroToken extends UsernamePasswordToken implements Serializable {

    private String stringPassword;

    private String tokenCustomAttribute1;

    private String tokenCustomAttribute2;

    private String tokenCustomAttribute3;

    private String tokenCustomAttribute4;

    public void setStringPassword(String stringPassword) {
        this.stringPassword = String.valueOf(super.getPassword());
    }
    /**
     * 因为父类的password是char类型 这里强行写成string 避免麻烦
     */
    public String getStringPassword() {
        return String.valueOf(this.getPassword());
    }

    public MyShiroToken(String email, String password) {
        super(email,password);
    }

    public MyShiroToken(String username, String password, boolean aBoolean, String host, String tokonFilter1, String tokonFilter2) {
        super(username,password,aBoolean,host);
        this.tokenCustomAttribute1=tokonFilter1;
        this.tokenCustomAttribute2=tokonFilter2;
    }


}
