package com.function.functional;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author zhangxuecheng4441
 * @date 2021/2/5/005 11:57
 */
public class DefaultFunctional {
    public static void main(String[] args) {
        Function<String, String> function = new Function<String, String>() {
            @Override
            public String apply(String s) {
                return null;
            }

            @Override
            public <V> Function<V, String> compose(Function<? super V, ? extends String> before) {
                return null;
            }

            @Override
            public <V> Function<String, V> andThen(Function<? super String, ? extends V> after) {
                return null;
            }
        };
        //exp
        boolean result = CollUtil.newArrayList("", 1, 'a').stream()
                //function
                .map(Object::toString)
                //predicate
                .anyMatch(StrUtil::isBlank);

        Predicate<String> predicate = new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return false;
            }

            @Override
            public Predicate<String> and(Predicate<? super String> other) {
                return null;
            }

            @Override
            public Predicate<String> negate() {
                return null;
            }

            @Override
            public Predicate<String> or(Predicate<? super String> other) {
                return null;
            }
        };

        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) {

            }

            @Override
            public Consumer<String> andThen(Consumer<? super String> after) {
                return null;
            }
        };

    }


}
