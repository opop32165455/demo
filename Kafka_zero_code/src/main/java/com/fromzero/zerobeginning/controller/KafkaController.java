package com.fromzero.zerobeginning.controller;

import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.fromzero.zerobeginning.constants.KafkaConsts;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @Desciption:
 * @Auther: ZhangXueCheng4441
 * @Date:2020/12/13/013 20:58
 */
@RequestMapping("/kakfa")
public class KafkaController   extends ApiController {

    @Resource
    private KafkaTemplate kafkaTemplate;

    /**
     *  往 test_topic_zxc 的 队列 里面传输数据info
     * @param info 数据
     * @return
     */
    @RequestMapping("/input")
    public R testKafka(String info){
        try {
            kafkaTemplate.send(KafkaConsts.ZXC_TEST_TOPIC,info);
            return success("成功");
        } catch (Exception e) {
            e.printStackTrace();
            return failed("false");
        }
    }




}
