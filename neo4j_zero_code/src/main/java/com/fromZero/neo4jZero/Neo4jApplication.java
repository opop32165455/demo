package com.fromZero.neo4jZero;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan(basePackages = "com.fromZero.neo4jZero.dao")
/***实体类扫描*/
@EntityScan(basePackages = "com.fromZero.neo4jZero.entity")
/***neo4j的repos扫描扫描*/
@EnableNeo4jRepositories(basePackages = "com.fromZero.neo4jZero.neo4jDao")
/***开启neo4j的注释*/
@EnableTransactionManagement
public class Neo4jApplication {

    public static void main(String[] args) {
        SpringApplication.run(Neo4jApplication.class, args);
    }

}
