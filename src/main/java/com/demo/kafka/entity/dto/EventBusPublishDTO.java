package com.demo.kafka.entity.dto;

import com.demo.kafka.entity.po.DeviceEventEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EventBusPublishDTO{
    @NotNull(message = "主题不能为空！")
    private String topic;

    @NotNull(message = "事件类型不能为空！")
    private DeviceEventEnum event;

    @NotNull(message = "设备类型不能为空！")
    private Integer clientType;

    @NotNull(message = "设备IMEI不能为空！")
    private String imei;

    private String param;
}
