package com.fromZero.zeroSchema;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.fromZero.zeroSchema.dao")
public class JsonSchemaApplication {

    public static void main(String[] args) {
        SpringApplication.run(JsonSchemaApplication.class, args);
    }

}
