package com.zero.listener.applicationEvent.listener;

import com.zero.listener.applicationEvent.entity.CustomEvent;
import io.vavr.Tuple;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.context.event.SmartApplicationListener;
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
public class SmartCustomListener implements SmartApplicationListener {

    /**
     * 事件判别
     *
     * @param eventType
     * @return
     */
    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return eventType == CustomEvent.class;
    }

    /**
     * 来源判别
     *
     * @param sourceType
     * @return
     */
    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        return true;
    }

    /**
     * 处理事件 业务逻辑
     *
     * @param event
     */
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        log.error("smart consume");
    }

    /**
     * 优先级
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
