package com.fromZero.zero;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.fromZero.zero.dao")
public class ZeroBeginningApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZeroBeginningApplication.class, args);
    }

}
