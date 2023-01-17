package com.zero;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author zhangxuecheng4441
 * @date 2022/4/15/015 17:47
 */
public class TestProducer {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.11.203.160:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "producer.client.id.demo");

        // 自定义分区器的使用
        //props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG,DefinePartitioner.class.getName());
        // 自定义拦截器使用
        //props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG,ProducerInterceptorPrefix.class.getName());
        props.put(ProducerConfig.ACKS_CONFIG,"all");

        Producer<String, String> producer = new KafkaProducer<>(props);
        for(int i = 0; i < 100; i++)
            producer.send(new ProducerRecord<String, String>("test", Integer.toString(i), Integer.toString(i)));

        producer.close();
    }
}
