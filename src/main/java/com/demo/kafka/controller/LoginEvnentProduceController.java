package com.demo.kafka.controller;


import com.demo.kafka.entity.dto.EventBusParallelPublishDTO;
import com.demo.kafka.entity.dto.EventBusPublishDTO;
import com.demo.kafka.entity.po.EventBus;
import com.demo.kafka.utils.ExcelReaderUtil;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping(value = "/login")
@Api(tags = "事件发布接口", value = "LoginEvnentProduceController")
public class LoginEvnentProduceController {

    @Autowired
    @Qualifier("protoKafka")
    private KafkaTemplate protoKafka;


    @PostMapping(value = "produce")
    @ApiOperation(value = "生产消息")
    public void produce(@RequestBody @Validated EventBusPublishDTO dto, BindingResult bindingResult){
        try {
            if(bindingResult.hasErrors()){
                log.error(bindingResult.getAllErrors().get(0).getDefaultMessage());
                return;
            }
            String topic = dto.getTopic();
            EventBus bus = new EventBus();
            BeanUtils.copyProperties(dto,bus);
            ListenableFuture<SendResult> future = protoKafka.send(topic,bus);
            future.addCallback(success -> log.info("KafkaMessageProducer 发送消息成功！"),
                    fail -> log.error("KafkaMessageProducer 发送消息失败！"));
        } catch (Exception e) {
            log.error("生产消息发生异常:{}", e);
        }
    }



    @PostMapping(value = "parallel")
    @ApiOperation(value = "批量生产消息")
    public void parallelProduce(@RequestBody @Validated EventBusParallelPublishDTO dto, BindingResult bindingResult){
        try {
            if(bindingResult.hasErrors()){
                log.error(bindingResult.getAllErrors().get(0).getDefaultMessage());
                return;
            }
            String topic = dto.getTopic();
            EventBus bus = new EventBus();
            bus.setClientType(dto.getClientType());
            bus.setEvent(dto.getEvent());
            List<String> imeis = ExcelReaderUtil.getColumnData(dto.getExcelFilePath());
            int deviceSize = imeis.size();
            CountDownLatch countDownLatch = new CountDownLatch(dto.getTotalCount());
            ScheduledThreadPoolExecutor schecdule = new ScheduledThreadPoolExecutor(Runtime.getRuntime().availableProcessors());
            ScheduledFuture scheduledFuture = schecdule.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    Long latchCount = countDownLatch.getCount();
                    int intLatchCount = latchCount.intValue();
                    int latchIndex = dto.getTotalCount() - intLatchCount;

                    int bigTestIndex = latchIndex%deviceSize;
                    String imei = imeis.get(bigTestIndex);
                    bus.setImei(imei);
                    ListenableFuture<SendResult> future = protoKafka.send(topic,bus);

                    countDownLatch.countDown();
                    future.addCallback(
                        success -> {
                            log.info("KafkaMessageProducer 发送消息成功！ index = {}", latchIndex + 1);
                        },
                        fail -> {
                            log.error("KafkaMessageProducer 发送消息失败！");
                        }
                    );
                }
            },0, dto.getPublishInterval(), TimeUnit.MILLISECONDS);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    scheduledFuture.cancel(true);
                    log.info("{}条消息全部发布成功！", dto.getTotalCount());
                }
            }).start();
        } catch (Exception e) {
            log.error("生产消息发生异常:{}", e);
        }
    }
}
