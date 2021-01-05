package com.fromZero.zeroShiro.shiro.service.impl;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.StrUtil;
import com.fromZero.zeroShiro.dao.SysPermissionDao;
import com.fromZero.zeroShiro.dao.SysRoleDao;
import com.fromZero.zeroShiro.entity.SysPermission;
import com.fromZero.zeroShiro.shiro.service.FilterChainDefinitionService;
import org.apache.shiro.config.Ini;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * shiro权限链操作
 *
 * @author zxc4441
 * @since 2020-12-23 21:59:55
 */
@Service(value = "filterChainDefinitionService")
public class FilterChainDefinitionServiceImpl implements FilterChainDefinitionService {

    private static final Logger LOG = LoggerFactory.getLogger(FilterChainDefinitionServiceImpl.class);

    private static final String CRLF = "\r\n";
    private static final String EQ = "=";
    private static final String FILTER1 = "//";
    private static final String FILTER2 = "#";
    private static final String TXT_FILE_NAME = "shiro_auth.txt";
    private static final String INI_FILE_NAME = "shiro_auth.ini";
    private static final String LOGIN_ROLE_FILTER = "login,role";

    /**
     * 其余角色权限分配
     */
    private static final String LAST_AUTH_CHAIN = "/** = user";

    @Resource
    @Lazy
    private ShiroFilterFactoryBean shiroFilterFactoryBean;
    @Resource
    SysRoleDao sysRoleDao;
    @Resource
    SysPermissionDao sysPermissionDao;

    //

    /**
     * 拼接权限字符串
     *
     * @return 拼接好的权限字符串
     */
    @Override
    public String loadFilterChainDefinitions() {
        StringBuffer sb = new StringBuffer("");
        //默认配置权限
        sb.append(filterChainFromTxt())
                //数据库配置权限
                .append(getDbAuthRule())
                //配置其余权限
                .append(LAST_AUTH_CHAIN);
        String chain = sb.toString();
        LOG.info("chain:[{}]", chain);
        return chain;
    }

    //todo
    //private String getRestfulOperationAuthRule() {
    //    List<SysPermission> promissions = sysPermissionDao.selectList(new QueryWrapper<>());
    //    Set<String> restfulUrls = new HashSet<String>();
    //    for (SysPermission promission : promissions) {
    //        restfulUrls.add(promission.getUrl());
    //    }
    //    StringBuffer sb = new StringBuffer("");
    //    for (Iterator<String> urls = restfulUrls.iterator(); urls.hasNext(); ) {
    //        String url = urls.next();
    //        if (!url.startsWith("/")) {
    //            url = "/" + url;
    //        }
    //        sb.append(url).append("=").append("login, rest[").append(url).append("]").append(CRLF);
    //    }
    //    return sb.toString();
    //}


    //根据角色，得到动态权限规则
    //获得权限

    /**
     * @return
     */
    private String getDbAuthRule() {
        StringBuffer stringBuffer = new StringBuffer("");
        //获取所有权限
        List<SysPermission> sysPermissions = sysPermissionDao.selectList(null);
        //获取拥有该权限的角色 并拼接权限过滤链
        for (SysPermission sysPermission : sysPermissions) {
            String url = sysPermission.getUrl();
            List<String> roleList = sysPermissionDao.selectRoleByPermissionId(sysPermission.getId());
            String rolesString = roleList.toString();
            stringBuffer.append(url).append(EQ).append(LOGIN_ROLE_FILTER).append(rolesString).append(CRLF);

        }
        //返回  /app/list/**=login,role[superManager, MiaoWaZhongZi, HuoQiuShu, YuanQiEr]
        //     /app/add/**=login,role[superManager, MiaoWaZhongZi]
        //     /app/update/**=login,role[superManager, HuoQiuShu]
        //     /app/delete/**=login,role[superManager, YuanQiEr]
        return stringBuffer.toString();
    }


    /**
     * 导入配置文件中的权限（txt方式）
     * 一开始没有找到ini导入攻略 就自己写了个导入规则qwq
     *
     * @return 权限控制链字符串
     */
    private String filterChainFromTxt() {
        String fileName = TXT_FILE_NAME;
        StringBuffer sb = new StringBuffer();
        try {
            //读取文件
            String source = ResourceUtil.readUtf8Str(fileName);
            List<String> info = Arrays.asList(StrUtil.split(source, CRLF));
            info.stream().filter(string -> !StrUtil.startWith(string, FILTER1)
                    && !StrUtil.startWith(string, FILTER2)
                    && StrUtil.isNotBlank(string))
                    .map(string -> string + CRLF)
                    .forEach(string -> sb.append(string));
        } catch (Exception e) {
            LOG.error("加载文件出错。file:[{}]", fileName);
        }
        return sb.toString();
    }

    /**
     * 导入配置文件中的权限（ini方式）
     *
     * @return 权限控制链字符串
     */
    private String FilterChainFromIni() {
        String fileName = INI_FILE_NAME;
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
        Ini ini = new Ini();
        try {
            assert inputStream != null;
            ini.load(inputStream);
        } catch (Exception e) {
            LOG.error("加载文件出错。file:[{}]", fileName);
        }
        String section = "base_auth";
        Set<String> keys = ini.get(section).keySet();
        StringBuffer sb = new StringBuffer();
        for (String key : keys) {
            String value = ini.addSection(section).get(key);
            sb.append(key)
                    .append(" = ")
                    .append(LOGIN_ROLE_FILTER)
                    .append(value)
                    .append(CRLF);
        }
        return sb.toString();
    }

    //根据数据库重构权限过滤链 （对权限进行修改时使用）
    //此方法加同步锁 更新所有

    /**
     * 用户重新设置权限 权限控制链 重构
     *
     * @return 新的权限配置集合
     */
    @Override
    public synchronized Map<String, String> reCreateFilterChains() {

        LOG.info("update filter chains....");
        AbstractShiroFilter shiroFilter = null;
        try {
            shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
        } catch (Exception e) {
            LOG.error("get ShiroFilter from shiroFilterFactoryBean error!", e);
            throw new RuntimeException("get ShiroFilter from shiroFilterFactoryBean error!");
        }
        PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
        DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();
        //清空老的权限控制
        manager.getFilterChains().clear();
        shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
        //重新使用导入方法 导入权限
        shiroFilterFactoryBean.setFilterChainDefinitions(loadFilterChainDefinitions());
        //重新构建生成
        Map<String, String> chains = shiroFilterFactoryBean.getFilterChainDefinitionMap();
        for (Map.Entry<String, String> entry : chains.entrySet()) {
            String url = entry.getKey();
            String chainDefinition = entry.getValue().trim();
            //String chainDefinition =entry.getValue().trim().replace(" ", "");
            manager.createChain(url, chainDefinition);
        }
        LOG.info("finish update filter chains!");
        return chains;

    }

}
