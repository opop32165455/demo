package com.zero.listener.rxjava.demo;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Subscriber;

/**
 * @author zhangxuecheng4441
 * @date 2021/9/23/023 15:10
 */
@Slf4j
public class DemoRx02 {

    public static void main(String[] args) {
//        create_demo();
//        just_demo();
//        from_demo();
        defer_demo();
//        range_demo();
//        repeat_demo();
    }

    /*
    14:16:59.385 [main] INFO top.onefine.rxjava.HelloWorld - onNext: 1
    14:16:59.387 [main] INFO top.onefine.rxjava.HelloWorld - onNext: 2
    14:16:59.387 [main] INFO top.onefine.rxjava.HelloWorld - onNext: 3
    14:16:59.390 [main] INFO top.onefine.rxjava.HelloWorld - onNext: 1
    14:16:59.390 [main] INFO top.onefine.rxjava.HelloWorld - onNext: 2
    14:16:59.390 [main] INFO top.onefine.rxjava.HelloWorld - onNext: 3
    14:16:59.390 [main] INFO top.onefine.rxjava.HelloWorld - onCompleted...
     */
    private static void repeat_demo() {
        Observable.range(1, 3).repeat(2)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        log.info("onCompleted...");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log.info("onError...");
                    }

                    @Override
                    public void onNext(Integer arg) {
                        log.info("onNext: {}", arg);
                    }
                });
    }

    /*
    14:15:30.089 [main] INFO top.onefine.rxjava.HelloWorld - onNext: 1
    14:15:30.092 [main] INFO top.onefine.rxjava.HelloWorld - onNext: 2
    14:15:30.092 [main] INFO top.onefine.rxjava.HelloWorld - onNext: 3
    14:15:30.092 [main] INFO top.onefine.rxjava.HelloWorld - onNext: 4
    14:15:30.092 [main] INFO top.onefine.rxjava.HelloWorld - onNext: 5
    14:15:30.092 [main] INFO top.onefine.rxjava.HelloWorld - onCompleted...
     */
    private static void range_demo() {
        Observable.range(1, 5)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        log.info("onCompleted...");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log.info("onError...");
                    }

                    @Override
                    public void onNext(Integer arg) {
                        log.info("onNext: {}", arg);
                    }
                });
    }

    /*
    14:11:17.146 [main] INFO top.onefine.rxjava.HelloWorld - onNext: Hello World.
    14:11:17.149 [main] INFO top.onefine.rxjava.HelloWorld - onCompleted...
     */
    private static String value;

    private static void defer_demo() {
        Observable<String> observable = Observable.defer(() -> Observable.just(value));

        value = "Hello World.";

        observable.subscribe(new Subscriber<String>() {
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
        });
    }

    /*
    11:49:42.936 [main] INFO top.onefine.rxjava.HelloWorld - onNext: 1
    11:49:42.939 [main] INFO top.onefine.rxjava.HelloWorld - onNext: 2
    11:49:42.939 [main] INFO top.onefine.rxjava.HelloWorld - onNext: 3
    11:49:42.939 [main] INFO top.onefine.rxjava.HelloWorld - onNext: 4
    11:49:42.939 [main] INFO top.onefine.rxjava.HelloWorld - onNext: 5
    11:49:42.939 [main] INFO top.onefine.rxjava.HelloWorld - onNext: 6
    11:49:42.939 [main] INFO top.onefine.rxjava.HelloWorld - onCompleted...
     */
    private static void from_demo() {
        // 文档：https://reactivex.io/documentation/operators/from.html
        Observable.from(new Integer[]{1, 2, 3, 4, 5, 6})
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        log.info("onCompleted...");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log.info("onError...");
                    }

                    @Override
                    public void onNext(Integer arg) {
                        log.info("onNext: {}", arg);
                    }
                });
    }

    /*
    11:43:49.006 [main] INFO top.onefine.rxjava.HelloWorld - onNext: RxJava学习...
    11:43:49.008 [main] INFO top.onefine.rxjava.HelloWorld - onCompleted...
     */
    private static void just_demo() {
        Observable.just("RxJava学习...")
                .subscribe(new Subscriber<String>() {
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
                });
    }

    /*
    11:44:26.187 [main] INFO top.onefine.rxjava.HelloWorld - onNext: RxJava学习...
     */
    private static void create_demo() {
        Observable.create((Observable.OnSubscribe<String>) subscriber -> subscriber.onNext("RxJava学习..."))
                .subscribe(new Subscriber<String>() {
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
                });
    }
}
