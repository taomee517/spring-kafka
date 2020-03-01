package com.demo.kafka.seriealizer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

@Slf4j
public class ProtobufSerializer implements Serializer<Object> {
    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public byte[] serialize(String s, Object obj) {
        return ProtobufSerializingUtil.serialize(obj);
    }

    @Override
    public void close() {

    }
}
