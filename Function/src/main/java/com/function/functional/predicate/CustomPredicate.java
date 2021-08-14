package com.function.functional.predicate;

import java.util.function.Predicate;

/**
 * @author zhangxuecheng4441
 * @date 2021/2/5/005 16:30
 */
public class CustomPredicate {

    public static String isAdult(Predicate<Integer> predicate) {

        boolean test = predicate.test(18);
        return null;

    }
}
