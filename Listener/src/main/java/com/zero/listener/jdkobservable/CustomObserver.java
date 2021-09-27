package com.zero.listener.jdkobservable;

import lombok.extern.slf4j.Slf4j;

import java.util.Observable;
import java.util.Observer;

/**
 * @author zhangxuecheng4441
 * @date 2021/8/14/014 17:27
 */
@Slf4j
public class CustomObserver implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof CustomObservable) {
            log.info("Custom consume data ...");
            CustomObservable boa = (CustomObservable)o;
            System.out.println("接收到了："+ arg);
        }
    }

}
