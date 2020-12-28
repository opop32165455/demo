package com.fromZero.zero;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.fromZero.zero.dao")
public class ZeroShiroApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(ZeroShiroApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
