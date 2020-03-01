package com.demo.kafka.constants;

import lombok.Getter;

import static com.demo.kafka.constants.KafkaTopic.*;

@Getter
public enum  TopicEnum {
    MIX(MIX_TEST_TOPIC),
    JSON(JSON_TEST_TOPIC),
    PROTOBUF(PROTOBUF_TEST_TOPIC),
    PARALLEL(PARALLEL_TEST_TOPIC);


    private String topic;

    TopicEnum(String topic){
        this.topic = topic;
    }
}
