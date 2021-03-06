package com.demo.kafka.seriealizer;

import com.demo.kafka.entity.po.Device;
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
        } catch (Exception e) {
            log.error("protobuf 反序列化发生异常：{}", e);
//            String data = new String(bytes);
//            device = JSON.parseObject(data,Device.class);
//            log.info("改用json反序列化结果： device = {}", device);
        }
        return device;

    }

    @Override
    public void close() {

    }
}
