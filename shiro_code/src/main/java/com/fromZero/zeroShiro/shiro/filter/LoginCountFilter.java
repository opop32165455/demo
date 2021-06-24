package com.fromZero.zeroShiro.shiro.filter;

import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.crazycake.shiro.RedisManager;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author zhangxuecheng4441
 * @date 2021/1/7/007 20:51
 */
public class LoginCountFilter extends AccessControlFilter {

    @Resource
    private ResourceUrlProvider resourceUrlProvider;

    /**
     * 踢出后到的地址
     */
    private String checkoutUrl;

    /**
     * 踢出之前登录的/之后登录的用户 默认踢出之前登录的用户
     */
    private boolean checkoutAfter = false;

    /**
     * 同一个帐号最大会话数 默认1
     */
    private int maxSession = 1;
    private SessionManager sessionManager;

    private RedisManager redisManager;

    public static final String DEFAULT_CHECKOUT_CACHE_KEY_PREFIX = "shiro:cache:checkout:";
    private String keyPrefix = DEFAULT_CHECKOUT_CACHE_KEY_PREFIX;

    public void setCheckoutUrl(String checkoutUrl) {
        this.checkoutUrl = checkoutUrl;
    }

    public void setCheckoutAfter(boolean checkoutAfter) {
        this.checkoutAfter = checkoutAfter;
    }

    public void setMaxSession(int maxSession) {
        this.maxSession = maxSession;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public void setRedisManager(RedisManager redisManager) {
        this.redisManager = redisManager;
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    private String getRedisCheckoutKey(String username) {
        return this.keyPrefix + username;
    }

    /**
     * 是否允许访问，返回true表示允许
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    /**
     * 表示访问拒绝时是否自己处理，如果返回true表示自己不处理且继续拦截器链执行，返回false表示自己已经处理了（比如重定向到另一个页面）。
     *
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
      return false;
    }

    private boolean isStaticFile(String path) {
        String staticUri = resourceUrlProvider.getForLookupPath(path);
        return staticUri != null;
    }

}