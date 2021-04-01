package com.xp.demo.kafka.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(KafkaTopicProperties.class)
public class KafKaTopicConfiguration {
    /**
     * 注入自定义的topic配置
     */
    @Autowired
    private KafkaTopicProperties topicProperties;

    @Bean
    public String[] kafkaTopicName() {
        return topicProperties.getTopicName();
    }

    @Bean
    public String topicGroupId() {
        return topicProperties.getGroupId();
    }
}
