package com.fromzero.zerobeginning.a_config;


import com.fromzero.zerobeginning.shiro.filter.TokenFilter;
import com.fromzero.zerobeginning.shiro.matcher.MyPasswordMatcher;
import com.fromzero.zerobeginning.shiro.realm.MyRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Desciption:
 * @Auther: ZhangXueCheng4441
 * @Date:2020/12/2/002 21:00
 */
@Configuration
public class shiroConfig {

    /**
     * Spring自动代理类 将Advisor的bean织入到其他bean（AOP）
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }
    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    /**
     * 配置shiro redisManager
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost("192.168.59.80");
        redisManager.setPort(6379);
        redisManager.setExpire(1800);// 配置缓存过期时间
        redisManager.setTimeout(0);
        redisManager.setPassword("anteater@!@!*");
        return redisManager;
    }

    /**
     * Session Manager
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        return sessionManager;
    }

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    /**
     * 限制同一账号登录同时登录人数控制
     *
     * @return
     */
    //@Bean
    //public KickoutSessionControlFilter kickoutSessionControlFilter() {
    //    KickoutSessionControlFilter kickoutSessionControlFilter = new KickoutSessionControlFilter();
    //    kickoutSessionControlFilter.setCacheManager(cacheManager());
    //    kickoutSessionControlFilter.setSessionManager(sessionManager());
    //    kickoutSessionControlFilter.setKickoutAfter(false);
    //    kickoutSessionControlFilter.setMaxSession(1);
    //    kickoutSessionControlFilter.setKickoutUrl("/auth/kickout");
    //    return kickoutSessionControlFilter;
    //}


    /**
     * 设置记住密码cookie
     *
     * @return
     */
    //@Bean("rememberMeCookie")
    //public SimpleCookie getRememberMeCookie() {
    //    // session name
    //    SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
    //    simpleCookie.setHttpOnly(true);
    //    //rememberMe time(30 day)
    //    simpleCookie.setMaxAge(2592000);
    //    //跨域cookie生效范围
    //    //simpleCookie.setDomain(domain);//使用未知
    //    return simpleCookie;
    //}

    /**
     * 设置rememberMe cookie
     *
     * @param rememberMeCookie bean name
     * @return
     */
    //@Bean("rememberMeManager")
    //public CookieRememberMeManager rememberMeManager(@Qualifier("rememberMeCookie") SimpleCookie rememberMeCookie) {
    //    CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
    //    cookieRememberMeManager.setCipherKey(Base64.decode("3AvVhmFLUs0KTA3Kprsdag=="));
    //    cookieRememberMeManager.setCookie(rememberMeCookie);
    //    return cookieRememberMeManager;
    //}

    /**
     * 配置自定义 权限认证+登陆验证
     *
     * @return shiro核心Realm
     */
    @Bean("customRealm")
    public MyRealm myShiroRealm() {
        //自定义的登陆和鉴权方式
        MyRealm myRealm = new MyRealm();
        //加入自己的密码验证方式
        myRealm.setCredentialsMatcher(new MyPasswordMatcher());
        return myRealm;
    }

    /**
     * @param myShiroRealm 自定义 权限认证+登陆验证
     *                     // * @param rememberMeManager 设置关闭浏览器记住面膜
     *                     // * @param sessionManager    自定义Session 管理器
     * @return 权限管理器
     */
    @Bean
    public SecurityManager securityManager(@Qualifier("customRealm") MyRealm myShiroRealm
           // , @Qualifier("rememberMeManager") CookieRememberMeManager rememberMeManager
            //, @Qualifier("sessionManager") CustomSessionManager sessionManager
    ) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //securityManager.setRememberMeManager(rememberMeManager);
        //securityManager.setSessionManager(sessionManager);
        securityManager.setRealm(myShiroRealm);
        return securityManager;
    }

    /**
     * 权限过滤器配置
     *
     * @param securityManager bean name
     *                        //* @param authService     bean name
     * @return 权限配置注册到工厂
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") SecurityManager securityManager
    ) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        HashMap<String, Filter> filterHashMap = new HashMap<>(10);
        ////自定义的token filter
        filterHashMap.put("tokenFilter", new TokenFilter());
        shiroFilterFactoryBean.setFilters(filterHashMap);
        //设置权限管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //设置跳转条件(已优化成api导入)
        Map<String, String> map = new LinkedHashMap<>();
        map.put("/js/**", "anon");
        map.put("/css/**", "anon");
        map.put("/open/**", "anon");
        map.put("/static/**", "anon");
        map.put("/login", "tokenFilter,anon");

        //登出
        map.put("/logout", "logout");
        map.put("/**", "anon");

        //配置默认登录url
        shiroFilterFactoryBean.setLoginUrl("/login.html");
        //首页
        shiroFilterFactoryBean.setSuccessUrl("/index.html");
        //错误页面，认证不通过跳转
        shiroFilterFactoryBean.setUnauthorizedUrl("/error.html");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map); //map形式导入
        return shiroFilterFactoryBean;
    }


    /**
     * @param securityManager 验证管理器
     * @return 开启权限注解功能 【 @RequiresPermissions("userInfo:test") 】
     */
    //@Bean
    //public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager securityManager) {
    //    AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
    //    //将认证管理器交给框架
    //    authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
    //    return authorizationAttributeSourceAdvisor;
    //}

}