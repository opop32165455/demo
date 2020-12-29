package com.fromZero.zeroKafka.handler;

import com.fromZero.zeroKafka.constants.KafkaConsts;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 消息处理器
 * </p>
 */
@Component
@Slf4j
public class MessageHandler {
    //消费入口  topics消费队列名字 containerFactory签收配置
    @KafkaListener(topics = KafkaConsts.ZXC_TEST_TOPIC, containerFactory = "ackContainerFactory")
    public void handleMessage(ConsumerRecord record, Acknowledgment acknowledgment) {
        try {
            String message = (String) record.value();
            log.info("收到消息: {}", message);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            // 手动提交 offset
            acknowledgment.acknowledge();
        }
    }
}
