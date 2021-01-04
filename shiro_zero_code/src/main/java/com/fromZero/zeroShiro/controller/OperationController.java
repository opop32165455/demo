package com.fromZero.zeroShiro.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangxuecheng4441
 * @date 2021/1/4/004 20:23
 */
@Slf4j
@RestController
@RequestMapping("/app")
public class OperationController {

    @GetMapping("/list/test")
    public String list() {
        return "list success!";
    }

    @GetMapping("/add/test")
    public String add() {
        return "add success!";
    }

    @GetMapping("/update/test")
    public String update() {
        return "update success!";
    }

    @GetMapping("/delete/test")
    public String delete() {
        return "delete success!";
    }
}

