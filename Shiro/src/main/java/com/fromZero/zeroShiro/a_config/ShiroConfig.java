package com.fromZero.zeroShiro.a_config;


import com.fromZero.zeroShiro.shiro.filter.LoginFilter;
import com.fromZero.zeroShiro.shiro.filter.RoleFilter;
import com.fromZero.zeroShiro.shiro.matcher.MyMd5WithSaltPasswordMatcher;
import com.fromZero.zeroShiro.shiro.realm.MyRealm;
import com.fromZero.zeroShiro.shiro.service.FilterChainDefinitionService;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.SessionListenerAdapter;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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
     * redis地址
     */
    @Value("${spring.redis.host}")
    private String host;

    /**
     * redis端口号
     */
    @Value("${spring.redis.port}")
    private Integer port;

    /**
     * redis密码
     */
    @Value("${spring.redis.password}")
    private String password;

    /**
     * redis 缓存时间
     */
    @Value("${spring.redis.timeout}")
    private Integer timeout;
    /**
     * redis 数据库id
     */
    @Value("${spring.redis.database}")
    private Integer database;


    /**
     * 配置shiro redisManager
     * 使用的是shiro-redis开源插件
     *
     * @return {@link RedisManager}
     */
    @Bean("redisManager")
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host + ":" + port);
        //redisManager.setPort(port);
        // 配置缓存过期时间
        //redisManager.setExpire(1800);
        redisManager.setTimeout(timeout);
        redisManager.setDatabase(database);
        redisManager.setPassword(password);
        return redisManager;
    }

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
    @Bean("redisCacheManager")
    public RedisCacheManager cacheManager(@Qualifier("redisManager") RedisManager redisManager) {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager);
        redisCacheManager.setKeyPrefix("shiro:cached:");
        return redisCacheManager;
    }


    /**
     * Session Manager
     * 使用的是shiro-redis开源插件
     *
     * @return {@link DefaultWebSessionManager}
     */
    @Bean("sessionManager")
    public DefaultWebSessionManager sessionManager(@Qualifier("redisSessionDAO") RedisSessionDAO redisSessionDAO) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        //关闭登陆重定向 url后带jsessionid的问题
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        sessionManager.setSessionDAO(redisSessionDAO);
        return sessionManager;
    }

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用的是shiro-redis开源插件
     */
    @Bean("redisSessionDAO")
    public RedisSessionDAO redisSessionDAO(@Qualifier("redisManager") RedisManager redisManager) {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager);
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


    /**
     * 设置记住密码cookie
     *
     * @return
     */
    @Bean("rememberMeCookie")
    public SimpleCookie getRememberMeCookie() {
        // session name
        SimpleCookie simpleCookie = new SimpleCookie("shiro_rememberMe");
        //set cookie的http only属性如果设为true的话，会增加对xss防护的安全系数。它有以下特点：
        //set cookie()的第七个参数
        //设为true后，只能通过http访问，javascript无法访问
        //防止xss读取cookie
        simpleCookie.setHttpOnly(true);
        //rememberMe time(30 day)
        simpleCookie.setMaxAge(2592000);
        //跨域cookie生效范围
        //simpleCookie.setDomain(domain);//使用未知
        return simpleCookie;
    }

    /**
     * 设置rememberMe cookie
     *
     * @param rememberMeCookie bean name
     * @return
     */
    @Bean("rememberMeManager")
    public CookieRememberMeManager rememberMeManager(@Qualifier("rememberMeCookie") SimpleCookie rememberMeCookie) {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.decode("3AvVhmFLUs0KTA3Kprsdag=="));
        cookieRememberMeManager.setCookie(rememberMeCookie);
        return cookieRememberMeManager;
    }

    ///**
    // * FormAuthenticationFilter 过滤器 过滤记住我
    // *
    // * @return 表单过滤器 todo 有点问题 会让静态页面访问不到
    // */
    //@Bean
    //public FormAuthenticationFilter formAuthenticationFilter() {
    //    FormAuthenticationFilter formAuthenticationFilter = new FormAuthenticationFilter();
    //    //对应前端的checkbox的name = rememberMe
    //    formAuthenticationFilter.setRememberMeParam("rememberMe");
    //    return formAuthenticationFilter;
    //}

    /**
     * 配置session监听
     *
     * @return
     */
    @Bean("sessionListener")
    public SessionListener sessionListener() {
        SessionListenerAdapter sessionListener = new SessionListenerAdapter();
        return sessionListener;
    }

    /**
     * 配置保存sessionId的cookie
     * 注意：这里的cookie 不是上面的记住我 cookie 记住我需要一个cookie session管理 也需要自己的cookie
     * 默认为: JSESSIONID 问题: 与SERVLET容器名冲突,重新定义为sid
     *
     * @return
     */
    @Bean("sessionIdCookie")
    public SimpleCookie sessionIdCookie() {
        //这个参数是cookie的名称
        SimpleCookie simpleCookie = new SimpleCookie("sid");
        //setcookie的httponly属性如果设为true的话，会增加对xss防护的安全系数。它有以下特点：

        //setcookie()的第七个参数
        //设为true后，只能通过http访问，javascript无法访问
        //防止xss读取cookie
        simpleCookie.setHttpOnly(true);
        simpleCookie.setPath("/");
        //maxAge=-1表示浏览器关闭时失效此Cookie
        simpleCookie.setMaxAge(-1);
        return simpleCookie;
    }

    ///**
    // * 配置会话管理器，设定会话超时及保存
    // * @return
    // */
    //@Bean("sessionManager")
    //public SessionManager sessionManager() {
    //    DefaultSessionManager sessionManager = new DefaultSessionManager();
    //    Collection<SessionListener> listeners = new ArrayList<SessionListener>();
    //    //配置监听
    //    listeners.add(sessionListener());
    //    sessionManager.setSessionListeners(listeners);
    //    sessionManager.set(sessionIdCookie());
    //    sessionManager.setSessionDAO(sessionDAO());
    //    sessionManager.setCacheManager(cacheManager());
    //    sessionManager.setSessionFactory(sessionFactory());
    //
    //    //全局会话超时时间（单位毫秒），默认30分钟  暂时设置为10秒钟 用来测试
    //    sessionManager.setGlobalSessionTimeout(1800000);
    //    //是否开启删除无效的session对象  默认为true
    //    sessionManager.setDeleteInvalidSessions(true);
    //    //是否开启定时调度器进行检测过期session 默认为true
    //    sessionManager.setSessionValidationSchedulerEnabled(true);
    //    //设置session失效的扫描时间, 清理用户直接关闭浏览器造成的孤立会话 默认为 1个小时
    //    //设置该属性 就不需要设置 ExecutorServiceSessionValidationScheduler 底层也是默认自动调用ExecutorServiceSessionValidationScheduler
    //    //暂时设置为 5秒 用来测试
    //    sessionManager.setSessionValidationInterval(3600000);
    //    //取消url 后面的 JSESSIONID
    //    sessionManager.setSessionIdUrlRewritingEnabled(false);
    //    return sessionManager;
    //
    //}

    /**
     * 配置自定义 权限认证+登陆验证
     *
     * @return shiro核心Realm
     */
    @Bean("customRealm")
    public MyRealm myShiroRealm(@Qualifier("myMd5WithSaltPasswordMatcher") MyMd5WithSaltPasswordMatcher myMd5Matcher) {
        //自定义的登陆和鉴权方式
        MyRealm myRealm = new MyRealm();
        //加入自己的密码验证方式
        myRealm.setCredentialsMatcher(myMd5Matcher);
        //启用身份验证缓存，即缓存AuthenticationInfo信息，默认false
        myRealm.setAuthenticationCachingEnabled(true);
        //启用授权缓存，即缓存AuthorizationInfo信息，默认false
        myRealm.setAuthorizationCachingEnabled(true);
        //缓存AuthenticationInfo信息的缓存名称
        myRealm.setAuthenticationCacheName("authenticationInfo");
        //缓存AuthorizationInfo信息的缓存名称
        myRealm.setAuthorizationCacheName("authorizationInfo");
        return myRealm;
    }

    /**
     * @param myShiroRealm 自定义 权限认证+登陆验证
     *                     // * @param rememberMeManager 设置关闭浏览器记住密码
     *                     // * @param sessionManager    自定义Session 管理器
     * @return 权限管理器
     */
    @Bean
    public SecurityManager securityManager(@Qualifier("customRealm") MyRealm myShiroRealm,
                                           @Qualifier("redisCacheManager") RedisCacheManager redisCacheManager,
                                           @Qualifier("sessionManager") DefaultWebSessionManager sessionManager,
                                           @Qualifier("rememberMeManager") CookieRememberMeManager rememberMeManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 自定义缓存实现 使用redis
        securityManager.setCacheManager(redisCacheManager);
        // 自定义session管理 使用redis
        securityManager.setSessionManager(sessionManager);
        //设置记住登陆状态
        securityManager.setRememberMeManager(rememberMeManager);
        securityManager.setRealm(myShiroRealm);
        return securityManager;
    }


    /**
     * 权限过滤器配置
     *
     * @param securityManager    安全管理器（核心）
     * @param filterChainService 自定义的权限过滤功能
     * @return shiro过滤器工厂
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
        shiroFilterFactoryBean.setFilters(filterHashMap);
        //设置权限管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        ////导入权限过滤链数据(使用map的形式配置)
        ////主要必须为LinkedHashMap 权限是有顺序之分的
        //Map<String, String> map = new LinkedHashMap<>();
        //map.put("/js/**", "anon");
        //map.put("/css/**", "anon");
        //map.put("/open/**", "anon");
        //map.put("/static/**", "anon");
        //map.put("/login", "anon");
        ////登出
        //map.put("/logout", "logout");
        //map.put("/**", "user");
        ////map形式导入
        //shiroFilterFactoryBean.setFilterChainDefinitionMap(map);


        //导入权限过滤链数据(String导入的形式)
        String definitions = filterChainService.loadFilterChainDefinitions();
        //整个权限过滤链string导入
        shiroFilterFactoryBean.setFilterChainDefinitions(definitions);
        //配置默认登录url
        shiroFilterFactoryBean.setLoginUrl("/login");
        //首页
        shiroFilterFactoryBean.setSuccessUrl("/afterLogin/index.html");
        //错误页面，认证不通过跳转
        shiroFilterFactoryBean.setUnauthorizedUrl("/beforeLogin/error.html");
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