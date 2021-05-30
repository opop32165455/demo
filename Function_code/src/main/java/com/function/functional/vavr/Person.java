package com.function.functional.vavr;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhangxuecheng4441
 * @date 2021/5/30/030 15:23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private String name;
    private int age;

    // setters and getters, toString
}
