package com.function.functional.function.impl;

import cn.hutool.core.collection.CollUtil;
import com.function.functional.function.CustomFunctionOne;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zhangxuecheng4441
 * @date 2021/2/5/005 14:13
 */
@Service("customFunctionOne")
@Slf4j
public class CustomFunctionOneImpl implements CustomFunctionOne {

    public static <T> Collection<?> listFilter(T data, Function<? super T, ? extends Collection<?>> function) {
        if (data instanceof List) {
            return function.apply(data);
        }
        return null;
    }

    public static void main(String[] args) {
        ArrayList<Object> list = CollUtil.newArrayList(1,2,3,2,1);
        List<?> filterList = (List<?>) listFilter(list, data -> data.stream().distinct().collect(Collectors.toList()));
        log.info("filterList:{}",filterList);
        HashSet<Integer> set = CollUtil.newHashSet(1, 2, 3, 4);
        log.error("---------------------------------");
        Collection<?> filterSet = listFilter(set, data -> data.stream().distinct().collect(Collectors.toList()));
        log.info("filterSet:{}",filterSet);

    }


}
