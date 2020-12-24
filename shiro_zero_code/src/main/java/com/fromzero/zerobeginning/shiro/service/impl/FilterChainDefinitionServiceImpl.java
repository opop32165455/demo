package com.fromzero.zerobeginning.shiro.service.impl;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fromzero.zerobeginning.dao.SysRoleDao;
import com.fromzero.zerobeginning.entity.SysPermission;
import com.fromzero.zerobeginning.entity.SysRole;
import com.fromzero.zerobeginning.shiro.service.FilterChainDefinitionService;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * shiro权限链操作
 *
 * @author zxc4441
 * @since 2020-12-23 21:59:55
 */
@Service
public class FilterChainDefinitionServiceImpl implements FilterChainDefinitionService {

    private static final Logger LOG = LoggerFactory.getLogger(FilterChainDefinitionServiceImpl.class);

    /**
     * /r/n前不能有空格
     */
    private static final String CRLF = "\r\n";

    /**
     * 其余角色权限分配
     */
    private static final String LAST_AUTH_CHAIN = "/**";

    @Resource
    @Lazy
    private ShiroFilterFactoryBean shiroFilterFactoryBean;

    @Resource
    SysRoleDao sysRoleDao;

    @Resource
    SysPermission sysPermissionDao;

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
        sb.append(getFixedAuthRule())
                //数据库配置权限
                .append(getDynaAuthRule())
                //配置其余权限
                .append(LAST_AUTH_STR);

        String chain = sb.toString();
        LOG.info("chain:[{}]", chain);
        return chain;
    }

    //生成restful风格功能权限规则
    private String getRestfulOperationAuthRule() {
        List<SysPermission> promissions = sysPermissionDao.selectList(new QueryWrapper<>());
        Set<String> restfulUrls = new HashSet<String>();
        for (SysPermission promission : promissions) {
            restfulUrls.add(promission.getUrl());
        }
        StringBuffer sb = new StringBuffer("");
        for (Iterator<String> urls = restfulUrls.iterator(); urls.hasNext(); ) {
            String url = urls.next();
            if (!url.startsWith("/")) {
                url = "/" + url;
            }
            sb.append(url).append("=").append("login, rest[").append(url).append("]").append(CRLF);
        }
        return sb.toString();
    }


    //根据角色，得到动态权限规则
    //获得权限
    private String getDynaAuthRule() {
        StringBuffer sb = new StringBuffer("");
        LinkedHashMap<String, Set<String>> rules = new LinkedHashMap<>();
        //获得所有权限（除了 没有母子权限 且parent_id不等于0）
        LinkedHashSet<String> permissions = getAllSortedUrls(0L);
        for (String url : permissions) {
            rules.put(url, new LinkedHashSet<>());
        }
        List<SysRole> roles = sysRoleDao.selectAll();
        //所有角色对应的url 存入rules key：url value：权限名
        for (SysRole role : roles) {
            for (SysPermission promission : role.getPermissions()) {
                String url = promission.getUrl();
                if (!url.startsWith("/")) {
                    url = "/" + url;
                }
                if (!rules.containsKey(url)) {
                    rules.put(url, new HashSet<>());
                }
                rules.get(url).add((role.getName()));
            }
        }
        //拼接权限
        for (Map.Entry<String, Set<String>> entry : rules.entrySet()) {
            sb.append(entry.getKey()).append(" = ").append("login,roleOrFilter").append(entry.getValue()).append(CRLF);
        }
        //返回 url1=login,oleOrFilter,权限名1 /r/n url2=login,oleOrFilter,权限名2 ...
        return sb.toString();
    }


    //得到固定权限验证规则串
    //获得xxxurl1=anno \r\n xxxurl2=anno \r\n xxxurl3=anno
    private String getFixedAuthRule() {
        String fileName = "classpath:shiro_base_auth.txt";
        StringBuffer sb = new StringBuffer();
        try {
            //读取文件
            String source = ResourceUtil.readUtf8Str(fileName);
            List<String> info = Arrays.asList(StrUtil.split(source, "\r\n"));
            info.stream().filter(String -> !StrUtil.startWith(String, "#")
                    && !StrUtil.startWith(String, "//")
                    && StrUtil.isNotBlank(String))
                    .map(string -> string + CRLF)
                    .forEach(string -> sb.append(string));
        } catch (Exception e) {
            LOG.error("加载文件出错。file:[{}]", fileName);
        }
        return sb.toString();
    }

    //根据数据库重构权限过滤链 （对权限进行修改时使用）
    //此方法加同步锁 更新所有
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
