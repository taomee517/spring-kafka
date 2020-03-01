package com.demo.kafka.consumer;

import com.demo.kafka.entity.po.Device;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.demo.kafka.constants.KafkaTopic.JSON_TEST_TOPIC;


@Slf4j
@Component
public class JsonDeviceConsumer {

    @KafkaListener(topics={JSON_TEST_TOPIC}, containerFactory = "mixJsonConsumerFactory", groupId = "json-device")
    public void listen(ConsumerRecord<String,Device> record){
        log.info("收到json-device消息：record = {}", record);
        Optional<?> kmsg = Optional.ofNullable(record.value());
        if(kmsg.isPresent()){
            Device device = record.value();
            log.info("设备上线,可执行后续业务逻辑：device = {}", device);
        }
    }

}
