package com.threads.controller;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.log4me.core.Factory.Log4iFactory;
import com.log4me.core.producer.DefaultProducer;
import com.threads.config.ThreadPools;
import com.threads.thread.base.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.stream.IntStream;

/**
 * @author zhangxuecheng4441
 * @date 2021/5/22/022 18:05
 */
@Controller
public class TestController {

    static final Log log = LogFactory.get();
    static Logger logger = LoggerFactory.getLogger("a");
    static final DefaultProducer LOG2 = Log4iFactory.getLogger(Test.class);


    @RequestMapping("test")
    public void test(){
        ThreadPools.getSimpleThreadPool().execute(()->{
            IntStream.range(0, 50).forEach(
                    i ->{ LOG2.debug("i am the log " + i);

                    }
            );
        });

        ThreadPools.getSimpleThreadPool().execute(()->{
            IntStream.range(0, 50).forEach(
                    (int i) -> {
                        LOG2.info("i am the log " + i);
                    }
            );
        });

        ThreadPools.getSimpleThreadPool().execute(()->{
            IntStream.range(0, 50).forEach(
                    i -> {
                        LOG2.error("i am the log " + i);
                    }
            );
        });
    }
}
