package com.demo.kafka.seriealizer;

import com.demo.kafka.entity.Device;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

@Slf4j
public class DeviceProtoSerializer implements Serializer<Device> {
    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public byte[] serialize(String s, Device device) {
        return ProtobufSerializingUtil.serialize(device);
    }

    @Override
    public void close() {

    }
}
