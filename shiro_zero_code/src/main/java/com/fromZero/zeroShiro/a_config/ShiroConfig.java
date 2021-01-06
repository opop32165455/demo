package com.fromZero.zeroShiro.a_config;


import com.fromZero.zeroShiro.shiro.filter.LoginFilter;
import com.fromZero.zeroShiro.shiro.filter.RoleFilter;
import com.fromZero.zeroShiro.shiro.matcher.MyMd5WithSaltPasswordMatcher;
import com.fromZero.zeroShiro.shiro.realm.MyRealm;
import com.fromZero.zeroShiro.shiro.service.FilterChainDefinitionService;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.UserFilter;
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

/**
 * @author R4441
 * @Desciption: shiro 核心配置类
 * @Auther: ZhangXueCheng4441
 * @Date:2020/12/2 21:00
 */
@Configuration
public class ShiroConfig {

    /**
     * Spring自动代理类 将Advisor的bean织入到其他bean（AOP）
     *
     * @return {@link DefaultAdvisorAutoProxyCreator }
     */
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultProxy = new DefaultAdvisorAutoProxyCreator();
        defaultProxy.setProxyTargetClass(true);
        return defaultProxy;
    }

    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     *
     * @return {@link RedisCacheManager}
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
     * @return {@link RedisManager}
     */
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost("192.168.59.80");
        redisManager.setPort(6379);
        // 配置缓存过期时间
        redisManager.setExpire(1800);
        redisManager.setTimeout(0);
        redisManager.setPassword("anteater@!@!*");
        return redisManager;
    }

    /**
     * Session Manager
     * 使用的是shiro-redis开源插件
     *
     * @return {@link DefaultWebSessionManager}
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        //关闭登陆重定向 url后带jsessionid的问题
        sessionManager.setSessionIdUrlRewritingEnabled(false);
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

    ///**
    // * 限制同一账号登录同时登录人数控制
    // *
    // * @return
    // */
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


    ///**
    // * 设置记住密码cookie
    // *
    // * @return
    // */
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

    ///**
    // * 设置rememberMe cookie
    // *
    // * @param rememberMeCookie bean name
    // * @return
    // */
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
        myRealm.setCredentialsMatcher(new MyMd5WithSaltPasswordMatcher());
        return myRealm;
    }

    /**
     * @param myShiroRealm 自定义 权限认证+登陆验证
     *                     // * @param rememberMeManager 设置关闭浏览器记住密码
     *                     // * @param sessionManager    自定义Session 管理器
     * @return 权限管理器
     */
    @Bean
    public SecurityManager securityManager(@Qualifier("customRealm") MyRealm myShiroRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 自定义缓存实现 使用redis
        securityManager.setCacheManager(cacheManager());
        // 自定义session管理 使用redis
        securityManager.setSessionManager(sessionManager());
        //设置记住登陆状态
        //securityManager.setRememberMeManager(rememberMeManager);
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
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") SecurityManager securityManager,
                                                         @Qualifier("filterChainDefinitionService") FilterChainDefinitionService filterChainService) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        HashMap<String, Filter> filterHashMap = new HashMap<>(10);
        //自定义的login filter
        filterHashMap.put("login", new LoginFilter());
        //自定义的role filter
        filterHashMap.put("role", new RoleFilter());
        //添加带有rememberMe功能的filter
        filterHashMap.put("userFilter", new UserFilter());
        shiroFilterFactoryBean.setFilters(filterHashMap);
        //设置权限管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);


        //导入权限过滤链数据(使用map的形式配置)
        //主要必须为LinkedHashMap 权限是有顺序之分的
        //Map<String, String> map = new LinkedHashMap<>();
        //map.put("/js/**", "anon");
        //map.put("/css/**", "anon");
        //map.put("/open/**", "anon");
        //map.put("/static/**", "anon");
        //map.put("/login", "anon");
        //登出
        //map.put("/logout", "logout");
        //map.put("/**", "user");
        //map形式导入
        // shiroFilterFactoryBean.setFilterChainDefinitionMap(map);


        //导入权限过滤链数据(String导入的形式)
        String definitions = filterChainService.loadFilterChainDefinitions();
        //整个权限过滤链string导入
        shiroFilterFactoryBean.setFilterChainDefinitions(definitions);
        //配置默认登录url
        shiroFilterFactoryBean.setLoginUrl("/login.html");
        //首页
        shiroFilterFactoryBean.setSuccessUrl("/index.html");
        //错误页面，认证不通过跳转
        shiroFilterFactoryBean.setUnauthorizedUrl("/error.html");
        return shiroFilterFactoryBean;
    }


    /**
     * @param securityManager 验证管理器
     * @return 开启权限注解功能 【 @RequiresPermissions("userInfo:test") 】
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        //将认证管理器交给框架
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}