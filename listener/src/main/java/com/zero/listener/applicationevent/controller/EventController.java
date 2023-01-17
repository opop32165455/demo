package com.zero.listener.applicationevent.controller;

import com.zero.listener.applicationevent.entity.CustomEvent;
import io.vavr.Tuple;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhangxuecheng4441
 * @date 2021/8/14/014 15:01
 */
@Slf4j
@RestController
public class EventController {

    @Resource
    ApplicationContext applicationContext;

    @PostMapping("/test")
    public void register(@RequestParam("name") String name,
                         @RequestParam("pwd") String pwd) {
        log.info("register...");
        applicationContext.publishEvent(new CustomEvent(this,Tuple.of(name, pwd)));

    }

}
