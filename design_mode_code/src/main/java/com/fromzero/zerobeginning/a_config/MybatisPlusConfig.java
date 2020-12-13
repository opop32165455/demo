package com.fromzero.zerobeginning.a_config;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.fromzero.zerobeginning.design_mode2.a1_strategy_demo_ordinary.AbstractHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashMap;

/**
 * <p>
 * mybatis-plus 配置
 * </p>
 *
 * @author zhangxuecheng
 * @date Created in 2018-11-08 17:29
 */
@Configuration
@EnableTransactionManagement
public class MybatisPlusConfig {
    /**
     * 性能分析拦截器，不建议生产使用
     * 显示sql执行时间 显示执行的sql语句
     */
    @Bean
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }


    @Bean("factoryHandlerMap")
    public HashMap<String, AbstractHandler> handlerHashMap(){
        HashMap<String, AbstractHandler> handlerHashMap = MapUtil.newHashMap();
        return handlerHashMap;
    }
}
