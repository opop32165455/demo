package com.zero.listener.applicationevent.entity;

import io.vavr.Tuple;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.ApplicationEvent;

/**
 * @author zhangxuecheng4441
 * @date 2021/8/14/014 15:08
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class CustomEvent extends ApplicationEvent {

    Tuple data;

    public CustomEvent(Object source, Tuple data) {
        super(source);
        this.data = data;
    }

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public CustomEvent(Object source) {
        super(source);
    }
}
