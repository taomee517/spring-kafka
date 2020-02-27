package com.demo.kafka.seriealizer;

import com.demo.kafka.entity.Device;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

@Slf4j
public class DeviceProtoDeserializer implements Deserializer<Device> {

    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public Device deserialize(String s, byte[] bytes) {
        Device device = null;

        try {
            device = ProtobufSerializingUtil.deserialize(bytes,Device.class);
            return device;
        } catch (Exception e) {
            log.error("protobuf 反序列化发生异常：{}", e);
            return null;
        }

    }

    @Override
    public void close() {

    }
}
