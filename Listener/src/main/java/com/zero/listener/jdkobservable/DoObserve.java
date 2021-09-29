package com.zero.listener.jdkobservable;

import io.vavr.Tuple;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangxuecheng4441
 * @date 2021/8/14/014 17:46
 */
@Slf4j
public class DoObserve {
    public static void main(String[] args) {

        CustomObservable customObservable = new CustomObservable();

        //将观察者放入集合中
        customObservable.addObserver((ob, arg) -> {
            log.info("Custom consume data ...");
            CustomObservable boa = (CustomObservable) ob;
            System.out.println("接收到了：" + arg);
        });

        //告诉观察者 可以执行一次
        customObservable.setChanged();

        //发送事件 集合中订阅的对象们 挨个处理订阅的信息
        customObservable.notifyObservers(Tuple.of(1, 2, 3, 4, 5));

    }


}
