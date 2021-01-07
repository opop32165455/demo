package com.fromZero.zeroShiro.shiro.matcher;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.crazycake.shiro.RedisManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * todo 校验密码错误次数
 *
 * @author zhangxuecheng4441
 * @date 2021/1/7/007 21:00
 */

public class WrongPasswordTimesMatcher extends SimpleCredentialsMatcher {
    private static final Logger log = LoggerFactory.getLogger(WrongPasswordTimesMatcher.class);

    public static final String DEFAULT_RETRYLIMIT_CACHE_KEY_PREFIX = "shiro:cache:retrylimit:";
    private String keyPrefix = DEFAULT_RETRYLIMIT_CACHE_KEY_PREFIX;

    private RedisManager redisManager;

    public void setRedisManager(RedisManager redisManager) {
        this.redisManager = redisManager;
    }

    private String getRedisKickoutKey(String username) {
        return this.keyPrefix + username;
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        //获取用户名
        String username = (String)token.getPrincipal();
        //获取用户登录次数

            //如果用户没有登陆过,登陆次数加1 并放入缓存

            //如果用户登陆失败次数大于5次 抛出锁定用户异常  并修改数据库字段

                //数据库字段 默认为 0  就是正常状态 所以 要改为1
                //修改数据库的状态字段为锁定


            //抛出用户锁定异常

        //判断用户账号和密码是否正确

            //如果正确,从缓存中将用户登录计数 清除

        return false;
    }

    /**
     * 根据用户名 解锁用户
     * @param username
     * @return
     */
    public void unlockAccount(String username){

    }
}
