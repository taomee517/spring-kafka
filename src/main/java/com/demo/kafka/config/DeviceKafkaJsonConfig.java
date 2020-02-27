package com.demo.kafka.config;

import com.demo.kafka.seriealizer.DeviceProtoDeserializer;
import com.demo.kafka.seriealizer.DeviceProtoSerializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DeviceKafkaJsonConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String brokers;



    //创建生产者配置map，ProducerConfig中的可配置属性比spring boot自动配置要多
    private Map<String, Object> producerProperties(){
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.ACKS_CONFIG, "-1");
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 5);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 500);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
        return props;
    }


    @Bean("jsonProduceFactory")
    public DefaultKafkaProducerFactory produceFactory(){
        return new DefaultKafkaProducerFactory(producerProperties());
    }


    /**
     * 不使用spring boot的KafkaAutoConfiguration默认方式创建的KafkaTemplate，重新定义
     * @param jsonProduceFactory
     * @return
     */
    @Bean(name = "jsonKafka")
    public KafkaTemplate kafkaTemplate(DefaultKafkaProducerFactory jsonProduceFactory){
        return new KafkaTemplate(jsonProduceFactory);
    }


}
