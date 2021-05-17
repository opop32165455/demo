package com.threads.thread.base;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author zhangxuecheng4441
 * @date 2021/2/18/018 20:58
 */
@Slf4j
public class NewFutureTask {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //特点：继承了runnable（配合线程） 和 future（能够返回值，抛出异常）
        //futureTask
        FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                log.info("print new future task thread and override call");
                Thread.sleep(3000);
                return "FutureTask return";
            }
        });
        new Thread(futureTask, "futureTask_thread").start();
        //注意：main线程中 get方法要等call方法执行完 才能拿到返回值 才会执行
        String result = futureTask.get();
        log.warn("result = {}", result);
        // futureTask by lambda
        FutureTask<Object> futureTaskLambda = new FutureTask<>(() -> {
            log.info("print new future task thread and override call by lambda");
            Thread.sleep(5000);
            return "FutureTask lambda return";
        });
        new Thread(futureTaskLambda, "futureTask_threadLambda").start();
        Object resultLambda = futureTaskLambda.get();
        log.warn("resultLambda = {}", resultLambda.toString());


    }
}
