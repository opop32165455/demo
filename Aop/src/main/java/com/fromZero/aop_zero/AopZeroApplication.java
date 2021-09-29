package com.fromZero.aop_zero;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author R4441 zxc
 */
@SpringBootApplication
@MapperScan(basePackages = "com.fromZero.aop_zero.dao")
public class AopZeroApplication {

    public static void main(String[] args) {
        SpringApplication.run(AopZeroApplication.class, args);
    }

}
