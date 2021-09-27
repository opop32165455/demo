package com.zero.listener.rxjava.demo;

import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import java.util.stream.IntStream;

/**
 *
 * @author zhangxuecheng4441
 * @date 2021/9/23/023 14:15
 */
@Slf4j
public class DemoRx03Schedulers {
    /**
     * todo 网络上对框架褒贬不一 尝试其他框架
     * @param args
     */
    public static void main(String[] args) {
        try {
            //// 1. 创建被观察者
            //Observable.range(1,  100)
            //        // 指定 subscribe() 发生在 IO 线程
            //        .subscribeOn(Schedulers.io())
            //        // 指定 Subscriber 的回调发生在主线程
            //        .observeOn(Schedulers.computation())
            //        .subscribe(new Action1<Integer>() {
            //            @Override
            //            public void call(Integer number) {
            //                log.info("number:" + number);
            //            }
            //        });

            Observable.create(new Observable.OnSubscribe<String>() {
                @Override
                public void call(Subscriber<? super String> subscriber) {
                    IntStream.range(0, 100).forEach(i -> {
                        String logs = "sub" + i;
                        subscriber.onNext(logs);
                        log.warn(logs);
                    });
                }
            })
                    .subscribeOn(Schedulers.io())
                    // 指定 Subscriber 的回调发生在主线程
                    .observeOn(Schedulers.newThread())
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            log.info("call:{}", s);
                        }
                    });

            Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
