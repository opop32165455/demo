package com.threads.thread.jdk.condition;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * @author Administrator
 */
public class ConditionExample1
{
    // 定义共享数据
    private static int shareData = 0;
    // 定义布尔变量标识当前的共享数据是否已经被使用
    private static boolean dataUsed = false;
    // 创建显式锁
    private static final Lock lock = new ReentrantLock();
    // ①使用显式锁创建Condition对象并且与之关联
    private static final Condition condition = lock.newCondition();

    // 对数据的写操作
    private static void change()
    {
        // 获取锁，如果当前锁被其他线程持有，则当前线程会进入阻塞
        lock.lock();
        try
        {
            // ②如果当前数据未被使用，则当前线程将进入wait队列，并且释放lock
            while (!dataUsed)
            {
                condition.await();
            }
            // 修改数据，并且将dataUsed状态标识为false
            TimeUnit.SECONDS.sleep(current().nextInt(5));
            shareData++;
            dataUsed = false;
            System.out.println("produce the new value: " + shareData);
            // ③ 通知并唤醒在wait队列中的其他线程——数据使用线程
            condition.signal();
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        } finally
        {
            // 释放锁
            lock.unlock();
        }
    }
    // 对数据进行使用
    private static void use()
    {
        // 获取锁，如果当前锁被其他线程持有，则当前线程会进入阻塞
        lock.lock();
        try
        {
            // ④ 如果当前数据已经使用，则当前线程将进入wait队列，并且释放lock
            while (dataUsed)
            {
                condition.await();
            }
            // 使用数据，并且将dataUsed状态标识置为true
            TimeUnit.SECONDS.sleep(current().nextInt(5));
            dataUsed = true;
            System.out.println("the shared data changed: " + shareData);
            // ⑤通知并唤醒wait队列中的其他线程——数据修改线程
            condition.signal();
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        } finally
        {
            // 释放锁
            lock.unlock();
        }
    }

    public static void main(String[] args)
    {
        // 创建并启动两个匿名线程
        new Thread(() ->
        {
            for (; ; ) {
                change();
            }
        }, "Producer").start();
        new Thread(() ->
        {
            for (; ; ) {
                use();
            }
        }, "Consumer").start();
    }
}