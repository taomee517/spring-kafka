package com.demo.kafka.consumer;

import com.alibaba.fastjson.JSON;
import com.demo.kafka.entity.po.Device;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Slf4j
@Component("simpleConsumer")
public class DemoConsumer {

    @KafkaListener(topics={"test2"}, groupId = "demo")
    public void listen(ConsumerRecord<?,?> record){
        log.info("收到demo-device消息：record = {}", record);
        Optional<?> kmsg = Optional.ofNullable(record.value());
        if(kmsg.isPresent()){
            Device device = JSON.parseObject((String) record.value(), Device.class);
            log.info("设备上线,可执行后续业务逻辑：device = {}", device);
        }
    }

}
