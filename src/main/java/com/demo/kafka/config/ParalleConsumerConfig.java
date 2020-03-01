package com.demo.kafka.config;

import com.demo.kafka.seriealizer.DeviceProtoDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ParalleConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String brokers;


    @Bean(name = "parallelConsumerFactory")
    //个性化定义消费者
    public ConcurrentKafkaListenerContainerFactory listenerContainerFactory() {
        //指定使用DefaultKafkaConsumerFactory
        ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(parallelConsumerFactory());
        //调整为手动ACK模式
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        //批量消费
        factory.setBatchListener(true);
        return factory;
    }

//    @Bean
//    public KafkaConsumer<String, Device> consumer(){
//        return ((KafkaConsumer) listenerContainerFactory().getConsumerFactory().createConsumer());
//    }


    //构造消费者属性map，ConsumerConfig中的可配置属性比spring boot自动配置要多
    private Map<String, Object> consumerProperties(){
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, DeviceProtoDeserializer.class);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 20);
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 10*60*1000);
        return props;
    }


    public DefaultKafkaConsumerFactory parallelConsumerFactory(){
        return new DefaultKafkaConsumerFactory(consumerProperties());
    }
}
