package com.demo.kafka.controller;


import com.demo.kafka.constants.SerializingType;
import com.demo.kafka.constants.TopicEnum;
import com.demo.kafka.entity.dto.DevicePublishDTO;
import com.demo.kafka.entity.po.Device;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController(value = "/deviceProducer")
@Api(tags = "设备通用发布接口", description = "DeviceProduceController")
public class DeviceProduceController {

    @Autowired
    @Qualifier("protoKafka")
    private KafkaTemplate protoKafka;

    @Autowired
    @Qualifier("jsonKafka")
    private KafkaTemplate jsonKafka;

    @PostMapping(value = "produce")
    @ApiOperation(value = "生产消息")
    public void produce(@RequestBody @Validated  DevicePublishDTO dto, BindingResult bindingResult){
        try {
            if(bindingResult.hasErrors()){
                log.error(bindingResult.getAllErrors().get(0).getDefaultMessage());
                return;
            }

            TopicEnum topicType = dto.getTopic();
            String topic = topicType.getTopic();
            Device device = new Device();
            BeanUtils.copyProperties(dto,device);
            ListenableFuture<SendResult> future = null;
            SerializingType serializingType = dto.getSerializingType();
            switch (serializingType){
                case JSON:
                    future = jsonKafka.send(topic,device);
                    break;
                case PROTOBUF:
                    future = protoKafka.send(topic,device);
                    break;
                default:
                    log.error("暂不支持的序列化方式");
                    break;
            }
            future.addCallback(success -> log.info("KafkaMessageProducer 发送消息成功！"),
                    fail -> log.error("KafkaMessageProducer 发送消息失败！"));
        } catch (Exception e) {
            log.error("生产消息发生异常:{}", e);
        }
    }
}
