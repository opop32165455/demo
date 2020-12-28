package com.fromZero.zero.shiro.service;

import java.util.Map;

/**
 * 权限相关信息操作接口
 *
 * @author zxc4441
 * @since 2020-12-23 21:59:55
 */
public interface FilterChainDefinitionService {

    /**
     * 加载过滤配置信息
     * @return
     */
    String loadFilterChainDefinitions();

    /**
     * 重新构建权限过滤器
     * 一般在修改了用户角色、用户等信息时，需要再次调用该方法
     */
    /**
     * 重新构建权限过滤器
     * 一般在修改了用户角色、用户等信息时，需要再次调用该方法
     *
     * @return
     */
    Map<String, String> reCreateFilterChains();
}
