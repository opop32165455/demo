package com.zero.listener.rxjava.demo;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Subscriber;

/**
 * @author zhangxuecheng4441
 * @date 2021/9/23/023 14:15
 */
@Slf4j
public class DemoRx {
    public static void main(String[] args) {
        // 1. 创建被观察者
        Observable<String> observable = Observable.create(subscriber -> {
            subscriber.onNext("Hello world.");
            throw new NullPointerException("Throw a Exception...");
//            subscriber.onCompleted();
        });

        // 2. 创建观察者
        Subscriber<String> subscriber = new Subscriber<String>() {

            @Override
            public void onCompleted() {
                log.info("onCompleted...");
            }

            @Override
            public void onError(Throwable e) {
                log.info("onError...");
            }

            @Override
            public void onNext(String s) {
                log.info("onNext: {}", s);
            }
        };

        // 3. 订阅事件
        observable.subscribe(subscriber);
    }
}
