package com.fromZero.zero;


import com.fromZero.zeroKafka.KafkaApplication;
import com.fromZero.zeroKafka.constants.KafkaConsts;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest(classes = KafkaApplication.class)
@RunWith(SpringRunner.class)
public class KafkaApplicationTests {


    @Resource
    private KafkaTemplate kafkaTemplate;
    /**
     * 测试发送消息
     */
    @Test
    public void testSend() {
        kafkaTemplate.send(KafkaConsts.ZXC_TEST_TOPIC, "hello,kafka...");
    }

}
