package com.example.asyn.completable;

import java.util.concurrent.CompletableFuture;

/**
 * @author zhangxuecheng4441
 * @date 2021/9/28/028 14:48
 */
public class CompletableFutureDemo1 {

    public static void main(String[] args) {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message");
        assert (cf.isDone());
        cf.getNow(null).varl
    }
}
