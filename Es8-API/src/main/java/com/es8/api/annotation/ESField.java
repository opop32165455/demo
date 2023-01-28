package com.es8.api.annotation;

import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * es字段,实体字段只有标明了该字段，才能够写入到es中
 *
 * @author zhangxuecheng4441
 * @date 2022/8/12/012 13:42
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface ESField {


    @Slf4j
    class Method {
        /**
         * bean 转 map 受ESField字段过滤
         *
         * @param obj obj
         * @param <T> obj T
         * @return obj map
         */
        public static <T> Map<String, Object> getMap(T obj) {
            ESField esAnno = obj.getClass().getAnnotation(ESField.class);
            //类上包含注解 所有字段写入es
            final boolean matchAllField = esAnno != null;

            return Arrays.stream(obj.getClass().getDeclaredFields())
                    .peek(field -> field.setAccessible(true))
                    //类或者字段上包含注解 则写入es
                    .filter(field -> matchAllField || field.getAnnotation(ESField.class) != null)
                    .collect(HashMap::new,
                            (map, field) -> {
                                try {
                                    map.put(field.getName(), field.get(obj));
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                            },
                            HashMap::putAll);
        }
    }
}
