package com.zero.listener.jdkObservable;

import io.vavr.Tuple;

/**
 * @author zhangxuecheng4441
 * @date 2021/8/14/014 17:46
 */
public class DoObserve {
    public static void main(String[] args) {

        CustomObservable customObservable = new CustomObservable();

        //将观察者放入对象中
        customObservable.addObserver(new CustomObserver());

        //告诉观察者 可以执行一次
        customObservable.setChanged();

        //发送事件
        customObservable.notifyObservers(Tuple.of(1,2,3,4,5));

    }


}
