package com.threads.thread.base;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangxuecheng4441
 * @date 2021/2/18/018 20:37
 */
@Slf4j
public class NewRunnable {

    public static void main(String[] args) {
        //任务和线程分开 写起来方便一些 毕竟实际中执行都是使用线程池
        //点进源码 thread中会去找runnable方法 如果存在执行
        //new runnable
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                log.info("print new runnable thread and override run");
            }
        };
        Thread runnableThread = new Thread(runnable,"runnable_thread");
        runnableThread.start();
        //new runnable by lambda
        Runnable runnableLambda = () -> log.info("print new runnable thread and override run by lambda");
        Thread runnableLambdaThread = new Thread(runnableLambda,"runnable_LambdaThread");
        runnableLambdaThread.start();

    }
}
