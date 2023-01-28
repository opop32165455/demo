package com.zero.listener.pub2subr.publish;

import com.zero.listener.pub2subr.subscrib.Subscriber;
import lombok.NonNull;

import java.util.Collection;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author zhangxuecheng4441
 * @date 2021/8/19/019 9:23
 */
public class Publisher<T> {


    /**
     * 存储容器
     */
    LinkedBlockingDeque<T> collection;

    /**
     * 总容量设置
     */
    public Publisher(int collectSize) {
        this.collection = new LinkedBlockingDeque<>(collectSize);
    }

    /**
     * 订阅者容器
     */
    private final Vector<Subscriber> subs = new Vector<>();

    public synchronized Publisher<T> addSub(@NonNull Subscriber sub) {
        if (!subs.contains(sub)) {
            subs.add(sub);
        }
        return this;
    }

    public synchronized void addData(@NonNull Collection<? extends T> data) {
        for (T datum : data) {
            try {
                collection.putFirst(datum);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void notifySub(Subscriber... subscribers) {
        Subscriber[] subArray;

        if (subscribers == null || subscribers.length == 0) {
            subArray = subs.toArray(new Subscriber[0]);
        } else {
            subArray = subscribers;
        }

        for (Subscriber subscriber : subArray) {
            subscriber.consumer(collection);
        }

    }

    public int getCollectionSize() {
        return collection.size();
    }

    public LinkedBlockingDeque<T> getCollection() {
        return collection;
    }
}
