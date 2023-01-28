package com.zero.listener.pub2subr.subscrib;

import java.util.Collection;

/**
 * @author zhangxuecheng4441
 * @date 2021/8/19/019 9:43
 */
public interface Subscriber {

    /**
     * 消费容器数据
     *
     * @param collection 数据
     */
    void consumer(Collection<?> collection);
}
