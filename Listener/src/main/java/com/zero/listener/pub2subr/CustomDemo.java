package com.zero.listener.pub2subr;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.json.JSONObject;
import com.zero.listener.pub2subr.publish.Publisher;
import com.zero.listener.pub2subr.subscrib.Subscriber;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author zhangxuecheng4441
 * @date 2021/8/19/019 10:11
 */
@Slf4j
public class CustomDemo {
    private static final ExecutorService SINGLE_THREAD_POOL = ThreadUtil.newExecutor(2, 2);
    private volatile static long CLOCK = System.currentTimeMillis();
    private static final int THRESHOLD = 5;
    private static final long READ_SPEED = 400L;
    private static final long WRITE_SPEED = 2 * 1000L;
    private static final long HUNGRY = 1000L;

    private static boolean READ_FINISH = false;
    private static boolean WRITE_FINISH = false;

    public static void main(String[] args) {
        //todo 选择一 订阅者处理（注意防止最后一批数据未处理）
        //订阅者一 3线程处理(可以部署在多个机器上)
        Subscriber writeSub = coll -> SINGLE_THREAD_POOL.execute(() -> consume((LinkedBlockingDeque<?>) coll));

        //订阅者二
        Subscriber subscriber = collection -> {
        };

        //设置最大对象存储数量10 防止对象过多oom + 注册订阅者
        Publisher<MainEvent> eventPublisher = new Publisher<MainEvent>(10)
                //.addSub(writeSub)
                .addSub(subscriber);

        //todo 定时器处理 定时处理线程
        ScheduledThreadPoolExecutor timer = new ScheduledThreadPoolExecutor(4, r -> new Thread(r, "consumer_timer"));
        timer.scheduleAtFixedRate(() -> timerConsume(eventPublisher), 0, 1, TimeUnit.SECONDS);

        //100次获取数据
        IntStream.range(0, 40).forEach(i -> {
            //模拟获取数据
            ThreadUtil.sleep(READ_SPEED);
            eventPublisher.addData(CollUtil.toList(MainEvent.builder().name(i).data(i).build()));
            log.info("read data [{}],data size:[{}]", i, eventPublisher.getCollectionSize());

            //广播给所有订阅者
            eventPublisher.notifySub();
        });
        READ_FINISH = true;

        for (int i = 0; i < THRESHOLD; i++) {
            if (WRITE_FINISH) {
                timer.shutdown();
                break;
            } else {
                log.info("wait write...");
                ThreadUtil.sleep(WRITE_SPEED);
            }
        }

        timer.shutdown();
    }

    private static void timerConsume(Publisher<MainEvent> eventPublisher) {
        LinkedBlockingDeque<MainEvent> events = eventPublisher.getCollection();
        consume(events);
        if (READ_FINISH && events.isEmpty()) {
            WRITE_FINISH = true;
        }
    }

    private static void consume(LinkedBlockingDeque<?> coll) {

        long now = System.currentTimeMillis();
        //判断写入程序是否饥渴
        boolean isHungry = now - CLOCK > HUNGRY;

        //数量过多达到阈值 5 或 已经5s没有处理数据了
        if (coll.size() > THRESHOLD || isHungry) {

            //处理一次数据
            CLOCK = System.currentTimeMillis();

            //从队列中取5个值处理
            List<JSONObject> list = dataHandle(coll);

            //模拟消费数据
            consumeDate(list);
        }
    }

    private static void consumeDate(List<JSONObject> list) {
        if (!list.isEmpty()) {
            ThreadUtil.sleep(WRITE_SPEED);
            log.warn("write data :{}", list);
        }
    }


    /**
     * 处理数据
     *
     * @param data 数据集合
     * @return 处理结果
     */
    private static List<JSONObject> dataHandle(LinkedBlockingDeque<?> data) {
        return Stream.generate(() -> 1)
                .limit(data.size())
                .map(s -> {
                    try {
                        return data.pollLast(10, TimeUnit.SECONDS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .map(JSONObject::new)
                .collect(Collectors.toList());
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    static class MainEvent {

        Object name;

        Object data;
    }
}
