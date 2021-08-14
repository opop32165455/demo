package com.zero.listener.jdkObservable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.Observable;

/**
 * @author zhangxuecheng4441
 * @date 2021/8/14/014 17:17
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomObservable extends Observable {

    @Override
    protected synchronized void setChanged() {

        super.setChanged();
    }
}
