package com.demo.kafka.entity.dto;

import com.demo.kafka.entity.po.DeviceEventEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EventBusParallelPublishDTO {
    @NotNull(message = "主题不能为空！")
    private String topic;

    @NotNull(message = "设备类型不能为空！")
    private Integer clientType;

    @NotNull(message = "列表文件地址不能为空！")
    private String excelFilePath;

    @NotNull(message = "发布次数不能为空！")
    private Integer totalCount;

    private DeviceEventEnum event;
    private Integer publishInterval = 100;
}
