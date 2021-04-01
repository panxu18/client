package com.xp.demo.kafka.service;

import com.xp.demo.kafka.model.MessageDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ConsumerService {
    /**
     * 监听topic
     *
     * @param record
     */
    @KafkaListener(topics = "topic1", groupId = "group1")
    public void processMessage(ConsumerRecord<Integer, String> record) {
        log.info("kafka process message start");
        log.info("topic={}, partition={}, msg={}", record.topic(), record.partition(), record.value());
        // do nothing
    }

    /**
     * 使用json反序列化消息
     * @param messageDto
     */
    @KafkaListener(groupId = "group2", topics = "topic1",
            properties = {"value.deserializer:org.springframework.kafka.support.serializer.JsonDeserializer"})
    public void processJsonMessage(MessageDto messageDto) {
        log.info("kafka process json message start");
        log.info("message={}", messageDto.toString());
        // do nothing
    }

    /**
     * 监听分区1
     *
     * @param record
     */
    @KafkaListener(groupId = "group3", topicPartitions = {
            @TopicPartition(topic = "topic1", partitions = "1")
    })
    public void processPartitionMessage(ConsumerRecord<Integer, String> record) {
        log.info("kafka process partition message start");
        log.info("topic={}, partition={}, msg={}", record.topic(), record.partition(), record.value());
        // do nothing
    }
}
