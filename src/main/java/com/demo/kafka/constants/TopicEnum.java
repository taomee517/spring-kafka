package com.demo.kafka.constants;

import lombok.Getter;

@Getter
public enum  TopicEnum {
    MIX(KafkaTopic.MIX_TEST_TOPIC),
    JSON(KafkaTopic.JSON_TEST_TOPIC),
    PROTOBUF(KafkaTopic.PROTOBUF_TEST_TOPIC);


    private String topic;

    TopicEnum(String topic){
        this.topic = topic;
    }
}
