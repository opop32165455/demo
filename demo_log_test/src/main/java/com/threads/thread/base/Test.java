package com.threads.thread.base;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.log4me.config.LogManager;
import com.log4me.core.Factory.Log4iFactory;
import com.log4me.core.consumer.ConsumerRunnable;
import com.log4me.core.producer.DefaultProducer;
import com.threads.config.ThreadPools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author zhangxuecheng4441
 * @date 2021/5/18/018 16:58
 */
@Component
public class Test {

    static final Log log = LogFactory.get();
    static Logger logger = LoggerFactory.getLogger("a");
    static final DefaultProducer LOG2 = Log4iFactory.getLogger(Test.class);

    public static void main2(String[] args) {
        log.info("ht log...........");
        logger.info("slf4 log.............");
        LOG2.info("my log ..................");
    }
    @PostConstruct
    public void initConsume(){
        boolean isStart = LogManager.isIsStart();
        if (isStart) {
            LogManager.getConsumerController().scheduleAtFixedRate(new ConsumerRunnable(), 1, 1, TimeUnit.SECONDS);
        }
    }

    public static void main(String[] args) {
       ThreadPools.getSimpleThreadPool().execute(()->{
           IntStream.range(0, 50).forEach(
                   i ->{ LOG2.debug("i am the log " + i);
                       logger.info("slf4 log.............");
                   }
           );
       });

        ThreadPools.getSimpleThreadPool().execute(()->{
            IntStream.range(0, 50).forEach(
                    (int i) -> {
                        LOG2.info("i am the log " + i);
                        logger.info("slf4 log.............");
                    }
            );
        });

        ThreadPools.getSimpleThreadPool().execute(()->{
            IntStream.range(0, 50).forEach(
                    i -> {
                        LOG2.error("i am the log " + i);
                        logger.info("slf4 log.............");
                    }
            );
        });
    }
}
