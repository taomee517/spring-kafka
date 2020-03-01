package com.demo.kafka.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class DefaultKafkaConfig {

    @Autowired
    KafkaProperties kafkaProperties;


    @Bean
    @Primary
    public DefaultKafkaConsumerFactory defaultKafkaConsumerFactory(){
        return new DefaultKafkaConsumerFactory(kafkaProperties.buildConsumerProperties());
    }

    @Bean
    @Primary
    public DefaultKafkaProducerFactory defaultKafkaProducerFactory(){
        return new DefaultKafkaProducerFactory(kafkaProperties.buildProducerProperties());
    }


    @Bean(name = "defaultListenerContainerFactory")
    @Primary
    public ConcurrentKafkaListenerContainerFactory listenerContainerFactory(DefaultKafkaConsumerFactory consumerFactory) {
        ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }


    @Bean("kafkaTemplate")
    @Primary
    public KafkaTemplate kafkaTemplate(DefaultKafkaProducerFactory producerFactory){
        return new KafkaTemplate(producerFactory);
    }

}
