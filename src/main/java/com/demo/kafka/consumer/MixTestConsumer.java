package com.demo.kafka.consumer;

import com.demo.kafka.entity.Device;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.demo.kafka.config.KafkaTopic.MIX_TEST_TOPIC;


@Slf4j
@Component
public class MixTestConsumer {

    @KafkaListener(topics={MIX_TEST_TOPIC}, containerFactory = "mixConsumerFactory")
    public void listen(ConsumerRecord<String,Device> record){
        log.info("收到mix消息：record = {}", record);
//        log.info("设备上线：{}", device);
        Optional<?> kmsg = Optional.ofNullable(record.value());
        if(kmsg.isPresent()){
            log.info("执行业务逻辑：value = {}", record.value());
        }
    }

}
