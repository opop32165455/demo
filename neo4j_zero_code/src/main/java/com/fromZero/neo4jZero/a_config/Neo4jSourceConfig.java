package com.fromZero.neo4jZero.a_config;


import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author R4441
 * @Desciption:
 * @Auther: ZhangXueCheng4441
 * @Date:2020/11/27/027 13:51
 */

@Configuration
public class Neo4jSourceConfig {

    @Value("${spring.data.neo4j.uri}")
    private String url;

    @Value("${spring.data.neo4j.username}")
    private String username;

    @Value("${spring.data.neo4j.password}")
    private String password;

    @Bean(name = "session")
    public Session neo4jSession() {
        Driver driver = GraphDatabase.driver(url, AuthTokens.basic(username, password));
        return driver.session();
    }
}