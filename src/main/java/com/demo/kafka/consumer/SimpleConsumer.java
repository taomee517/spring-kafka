package com.demo.kafka.consumer;

import com.demo.kafka.entity.Device;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component("simpleConsumer")
public class SimpleConsumer {

    @KafkaListener(topics={"test2"})
    public void listen(Device device){
        log.info("设备上线：{}", device);
    }

}
