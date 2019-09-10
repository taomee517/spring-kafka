package com.demo.kafka.consumer;

import com.alibaba.fastjson.JSON;
import com.demo.kafka.entity.Device;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component("simpleConsumer")
public class SimpleConsumer {

    @KafkaListener(topics={"test2"})
    public void listen(Device device){
//        String data
//        Device device = JSON.parseObject(data,Device.class);
        log.info("设备上线：{}", device);
    }

}
