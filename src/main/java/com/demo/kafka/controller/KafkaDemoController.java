package com.demo.kafka.controller;


import com.alibaba.fastjson.JSON;
import com.demo.kafka.entity.po.Device;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController(value = "/kafka")
@Api(tags = "Kafka测试", description = "KafkaDemoController")
public class KafkaDemoController {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public static final String TOPIC = "test2";

    @PostMapping(value = "produce")
    @ApiOperation(value = "生产消息")
    public void produce(@RequestBody Device device){
        try {
            long start = System.currentTimeMillis();
            ListenableFuture<SendResult> future = kafkaTemplate.send(TOPIC, JSON.toJSONString(device));
            future.addCallback(success -> log.info("KafkaMessageProducer 发送消息成功！"),
                    fail -> log.error("KafkaMessageProducer 发送消息失败！"));
            long end = System.currentTimeMillis();
            log.info("kafka写入速度为：{}",end-start);
        } catch (Exception e) {
            log.error("生产消息发生异常:{}", e);
        }
    }
}
