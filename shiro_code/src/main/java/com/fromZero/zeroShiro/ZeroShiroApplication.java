package com.fromZero.zeroShiro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zxc
 */
@SpringBootApplication
@MapperScan(basePackages = "com.fromZero.zeroShiro.dao")
public class ZeroShiroApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(ZeroShiroApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
