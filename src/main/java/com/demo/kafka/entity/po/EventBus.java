package com.demo.kafka.entity.po;

import lombok.Data;

import java.io.Serializable;

@Data
public class EventBus implements Serializable {
    private DeviceEventEnum event;
    private Integer clientType;
    private String imei;
    private Long timestamp;
    private String param;
}
