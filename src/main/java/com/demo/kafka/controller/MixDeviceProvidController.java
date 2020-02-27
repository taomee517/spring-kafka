package com.demo.kafka.controller;


import com.demo.kafka.entity.MixDevice;
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

import javax.annotation.Resource;

import static com.demo.kafka.config.KafkaTopic.MIX_TEST_TOPIC;

@Slf4j
@RestController(value = "/mixDeviceProvider")
@Api(tags = "Kafka Device发布测试", description = "kafka")
public class MixDeviceProvidController {

    @Autowired
    @Qualifier("kafkaTemplate")
    private KafkaTemplate kafkaTemplate;

    @Autowired
    @Qualifier("jsonKafka")
    private KafkaTemplate jsonKafka;

    @PostMapping(value = "mixProtobuf")
    @ApiOperation(value = "生产消息-protobuf序列化")
    public void produceWithProtobuf(@RequestBody MixDevice device){
        try {
            ListenableFuture<SendResult> future = kafkaTemplate.send(MIX_TEST_TOPIC, device);
            future.addCallback(success -> log.info("produceWithProtobuf device 发送消息成功！"),
                    fail -> log.error("produceWithProtobuf device 发送消息失败！"));
        } catch (Exception e) {
            log.error("protobuf序列化方式生产消息发生异常:{}", e);
        }
    }


    @PostMapping(value = "mixJson")
    @ApiOperation(value = "生产消息-json序列化")
    public void produceWithJson(@RequestBody MixDevice device){
        try {
            ListenableFuture<SendResult> future = jsonKafka.send(MIX_TEST_TOPIC, device);
            future.addCallback(success -> log.info("produceWithJson device 发送消息成功！"),
                    fail -> log.error("produceWithJson device 发送消息失败！"));
        } catch (Exception e) {
            log.error("json序列化方式生产消息发生异常:{}", e);
        }
    }
}
