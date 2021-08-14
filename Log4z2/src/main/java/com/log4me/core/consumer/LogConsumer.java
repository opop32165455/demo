package com.log4me.core.consumer;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.log4me.exception.IllegalLoggerArgumentException;

import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.function.Consumer;

/**
 * @author zhangxuecheng4441
 * @date 2021/5/21/021 11:18
 */
public class LogConsumer<T extends Queue<?>> implements Consumer<T> {

    @Override
    public void accept(T queue) {
        if (queue == null) {
            throw new IllegalLoggerArgumentException("queue is null consumer can not start");
        }
        Object remove = null;
        try {
            remove = queue.remove();
            System.out.println("remove = " + remove.toString());
        } catch (NoSuchElementException e) {
            System.out.println("empty queue");
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override
    public Consumer<T> andThen(Consumer<? super T> after) {
        if (after == null) {
            throw new IllegalLoggerArgumentException("after consumer is null consumer can not start");
        }
        return (T queue) -> {
            this.accept(queue);
            int size = queue.size();
            System.out.println(StrUtil.format("After consume {} [Thread-{}] then queue surplus [{}] element ",
                    DateUtil.now(), Thread.currentThread().getName(), size));
        };
    }


    public Consumer<T> before(Consumer<T> before) {
        if (before == null) {
            throw new IllegalLoggerArgumentException("Before consumer is null consumer can not start");
        }
        return (T queue) -> {
            int size = queue.size();
            System.out.println(StrUtil.format("before consume {} [Thread-{}] then queue surplus [{}] element ",
                    DateUtil.now(), Thread.currentThread().getName(), size));
            before.andThen(this).accept(queue);
        };
    }
}
