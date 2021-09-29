package com.example.asyn.completable;

import cn.hutool.core.thread.ThreadUtil;
import com.example.asyn.pool.ThreadPools;
import lombok.extern.slf4j.Slf4j;
import lombok.var;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

/**
 * @author zhangxuecheng4441
 * @date 2021/9/28/028 14:48
 */
@Slf4j
public class CompletableFutureDemo1 {

    public static void main(String[] args) {
        //CompletableFuture<String> cf = CompletableFuture.completedFuture("message");
        //assert (cf.isDone());
        //var now = cf.getNow(null);
        //System.out.println("now = " + now);

        //CompletableFuture<Void> cf2 = CompletableFuture.runAsync(() -> {
        //    var daemon = Thread.currentThread().isDaemon();
        //    System.out.println("daemon = " + daemon);
        //    ThreadUtil.sleep(2000);
        //});
        //
        //assertFalse(cf2.isDone());
        //ThreadUtil.sleep(4000);
        //assertTrue(cf2.isDone());

        //CompletableFuture<String> cf = CompletableFuture.completedFuture("message").thenApply(s -> {
        //    assertFalse(Thread.currentThread().isDaemon());
        //    return s.toUpperCase();
        //});
        //assertEquals("MESSAGE", cf.getNow(null));

        //thenApplyAsyncExample();
        //thenApplyAsyncWithExecutorExample();
        //completeExceptionallyExample();
        //cancelExample();
        runAfterBothExample();
    }


    static void thenApplyAsyncExample() {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message")
                .thenApplyAsync(s -> {
                    assertTrue(Thread.currentThread().isDaemon());
                    ThreadUtil.sleep(2000);
                    return s.toUpperCase();
                });
        assertNull(cf.getNow(null));
        assertEquals("MESSAGE", cf.join());
    }

    static void thenApplyAsyncWithExecutorExample() {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message").thenApplyAsync(s -> {
            var name = Thread.currentThread().getName();
            System.out.println("name = " + name);
            log.info("name = " + name);
            ThreadUtil.sleep(2000);
            return s.toUpperCase();
        }, ThreadPools.getSimpleThreadPool());

        assertNull(cf.getNow(null));
        var join = cf.join();
        System.out.println("join = " + join);
        log.info("join = " + join);
    }

    static void completeExceptionallyExample() {
        CompletableFuture<String> cf = CompletableFuture
                .completedFuture("message")
                .thenApplyAsync(s1 -> {
                    ThreadUtil.sleep(2000);
                    return s1.toUpperCase();
                }, ThreadPools.getSimpleThreadPool());

        CompletableFuture<String> exceptionHandler = cf.handle((s, th) -> (th != null) ? "message upon cancel" : "");

        cf.completeExceptionally(new RuntimeException("completed exceptionally"));
        assertTrue("Was not completed exceptionally", cf.isCompletedExceptionally());
        try {
            cf.join();
            fail("Should have thrown an exception");

        }
        // just for testing
        catch (CompletionException ex) {
            assertEquals("completed exceptionally", ex.getCause().getMessage());
        }

        assertEquals("message upon cancel", exceptionHandler.join());
    }

    static void cancelExample() {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message").thenApplyAsync(String::toUpperCase,
                ThreadPools.getSimpleThreadPool());
        CompletableFuture<String> cf2 = cf.exceptionally(throwable -> "canceled message");
        assertTrue("Was not canceled", cf.cancel(true));
        assertTrue("Was not completed exceptionally", cf.isCompletedExceptionally());
        assertEquals("canceled message", cf2.join());
    }


    static void applyToEitherExample() {
        //String original = "Message";
        //CompletableFuture cf1 = CompletableFuture.completedFuture(original)
        //        .thenApplyAsync(s -> delayedUpperCase(s));
        //CompletableFuture cf2 = cf1.applyToEither(
        //        CompletableFuture.completedFuture(original).thenApplyAsync(s -> delayedLowerCase(s)),
        //        s -> s + " from applyToEither");
        //assertTrue(cf2.join().endsWith(" from applyToEither"));
    }


    static void runAfterBothExample() {
        String original = "Message";
        var func = CompletableFuture.completedFuture(original)
                //A方法执行
                .thenApplyAsync(s -> {
                    log.info(s.toUpperCase() + 11111111);
                    ThreadUtil.sleep(2000);
                    return s.toUpperCase() + 11111111;
                }, ThreadPools.getSimpleThreadPool())
                .thenCombine( //b方法执行
                        CompletableFuture.completedFuture(original)
                                .thenApplyAsync(s -> {
                                    log.info(s.toLowerCase() + 22222222);
                                    return s.toLowerCase() + 22222222;
                                }, ThreadPools.getSimpleThreadPool()), (s1, s2) -> s1 + s2)
                //绑定
                .thenCombine(
                        //b方法执行
                        CompletableFuture.completedFuture(original)
                                .thenApplyAsync(s -> {
                                    log.info(s.toLowerCase() + 33333333);
                                    return s.toLowerCase() + 33333333;
                                }, ThreadPools.getSimpleThreadPool()),
                        //结果操作
                        (s1, s2) -> s1 + s2
                );
        var joinResult = func.join();
        log.info(joinResult);
    }

}

