package com.fromZero.zero2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author R4441
 */
@SpringBootApplication
@MapperScan(basePackages = "com.fromZero.zero2.dao")
public class ZeroApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZeroApplication.class, args);
    }

}
