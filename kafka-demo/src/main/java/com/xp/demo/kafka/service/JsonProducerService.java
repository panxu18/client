package com.xp.demo.kafka.service;

import com.xp.demo.kafka.model.MessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Slf4j
@Service
public class JsonProducerService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public JsonProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String key, String data) {
        log.info("kafka sendMessage start");
        // json serialize
        MessageDto messageDto = new MessageDto();
        messageDto.setId(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        messageDto.setTitle(data);
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, key, messageDto);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("kafka sendMessage error", ex);
            }

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                log.info("kafka sendMessage success");
            }
        });
    }
}
