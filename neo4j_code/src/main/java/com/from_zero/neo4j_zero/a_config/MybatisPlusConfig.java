package com.from_zero.neo4j_zero.a_config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

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
}
