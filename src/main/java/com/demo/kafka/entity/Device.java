package com.demo.kafka.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author LuoTao
 * @email taomee517@qq.com
 * @date 2019/1/8
 * @time 9:11
 */
@Data
@Accessors(chain = true)
public class Device implements Serializable {
    /**设备id*/
    private Integer id;

    /**设备imei*/
    private String imei;

    /**设备类型*/
    private String type;
}
