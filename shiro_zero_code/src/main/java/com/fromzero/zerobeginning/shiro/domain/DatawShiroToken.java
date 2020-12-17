package com.fromzero.zerobeginning.shiro.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.shiro.authc.UsernamePasswordToken;

import java.io.Serializable;

/**
 * 自定义 封装的shiro框架使用的tokon
 * @author lin
 * @since 2017/4/7.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatawShiroToken extends UsernamePasswordToken implements Serializable {
    /**
     * 父类属性
     * @param private String username;
     * @param private char[] password;
     * @param  private boolean rememberMe;
     * @param  private String host;
     */
    //密码 shiro自带的密码是char类型 为了避免麻烦 使用string的passwd
    private String passwd;
    //时间戳 (用于加密)
    private String timestamp;
    //随机数（用于加密）
    private String rand;
    //时区
    private String timeZone;
    //语言
    private String lang;


    public DatawShiroToken(String username, String passwd, String timeZone, String lang) {
        super(username, passwd);
        this.passwd = passwd;
        this.lang = lang;
        this.timeZone = timeZone;
    }

    public DatawShiroToken(String username, String passwd) {
        super(username, passwd);
        this.passwd = passwd;
    }

    public DatawShiroToken(String username, String passwd, String timeZone, String lang, String timestamp, String rand) {
        super(username, passwd);
        this.passwd = passwd;
        this.lang = lang;
        this.timeZone = timeZone;
        this.timestamp = timestamp;
        this.rand = rand;
    }
    public DatawShiroToken(String username, String passwd, String host, boolean isMemberMe) {
        super(username, passwd.toCharArray(),isMemberMe,host);
        this.passwd = passwd;
    }

}
