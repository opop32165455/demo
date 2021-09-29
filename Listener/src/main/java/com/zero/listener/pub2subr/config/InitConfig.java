package com.zero.listener.pub2subr.config;

import lombok.Data;

import java.util.Collection;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author zhangxuecheng4441
 * @date 2021/8/19/019 9:53
 */
@Data
public class InitConfig<T1> {

    Collection<T1> collection;


    public InitConfig() {
        this.collection = new LinkedBlockingDeque<>();
    }
}
