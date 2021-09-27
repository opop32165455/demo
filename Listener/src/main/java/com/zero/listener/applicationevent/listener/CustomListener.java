package com.zero.listener.applicationevent.listener;

import com.zero.listener.applicationevent.entity.CustomEvent;
import io.vavr.Tuple;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangxuecheng4441
 * @date 2021/8/14/014 15:12
 */
@Component
@Slf4j
public class CustomListener {

    private static final List<Tuple> DATA = new LinkedList<>();
    private final static int THRESHOLD = 5;
    private static long CLOCK = System.currentTimeMillis();

    /**
     * 10秒必处理一次数据
     */
    @PostConstruct
    public static void initTimer() {
        log.info("timer init ... ");
        new ScheduledThreadPoolExecutor(1, r -> new Thread(r, "consumer_timer"))
                .scheduleAtFixedRate(() -> { log.info("timer consume data");print(); },
                        1,
                        10,
                        TimeUnit.SECONDS);

    }

    /**
     * 如果离上一次处理数据已经10s了 马上处理数据
     *
     * @return
     */
    public boolean timeConsume() {
        long now = System.currentTimeMillis();
        boolean isHand = now - CLOCK > 10000L;
        if (isHand) {
            log.info("is time to consume");
            CLOCK = now;
        }
        return isHand;
    }


    /**
     * EventListener 的底层原理是 BeanPostProcessor后置处理器
     * <p>
     * 本质上 并不是监听和发布并不是一个异步的
     * 在事件发布到容器之后 执行后置处理 然后就会执行注册到listen的一些业务方法
     * <p>
     * 底层并没有队列 只有一个存储事件的有序的linked set集合
     * 在发布后必然是要执行才能发布结束的
     * <p>
     * 网上文章大部分spring的异步注解 去处理后续的一些任务 达成异步执行
     * 当然也可以自己维护线程池 和一些任务队列 方便增加对大量任务的一些取舍的策略
     *
     * @param customEvent
     */
    @EventListener(CustomEvent.class)
    public void listen(CustomEvent customEvent) {
        DATA.add(customEvent.getData());
        //数据超过五个 立即处理
        if (DATA.size() > THRESHOLD || timeConsume()) {
            print();
        }
    }


    public static void print() {
        DATA.forEach(d -> log.warn("consume {}", d));
        //消费完数据清楚
        DATA.clear();
    }

}
