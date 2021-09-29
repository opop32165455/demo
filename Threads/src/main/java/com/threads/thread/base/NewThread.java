package com.threads.thread.base;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangxuecheng4441
 * @date 2021/2/18/018 20:22
 */
@Slf4j
public class NewThread {

    public static void main(String[] args) {

        //new thread
        Thread newThread = new Thread("new_thread") {
            @Override
            public void run() {
                log.info("print new thread and override run");
            }
        };
        newThread.start();
        //new thread by lambda
        Thread newThreadLambda = new Thread(() -> log.info("print new thread override run by lambda"));
        newThreadLambda.setName("new_thread_lambda");
        newThreadLambda.start();

    }
}
