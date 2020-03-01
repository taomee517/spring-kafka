package com.demo.kafka.entity.dto;

import com.demo.kafka.constants.SerializingType;
import com.demo.kafka.constants.TopicEnum;
import com.demo.kafka.entity.po.Device;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DevicePublishDTO extends Device {
    @NotNull(message = "主题不能为空！")
    private TopicEnum topic;

    @NotNull(message = "序列化方式不能为空！")
    private SerializingType serializingType;
}
