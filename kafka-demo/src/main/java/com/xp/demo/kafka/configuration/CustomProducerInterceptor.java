package com.xp.demo.kafka.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class CustomProducerInterceptor implements ProducerInterceptor {
    private AtomicInteger errorCounter;
    private AtomicInteger successCounter;

    @Override
    public ProducerRecord onSend(ProducerRecord record) {
       return record;
    }

    /**
     * 消息发送发送被应答或者发送失败时计数
     * @param metadata
     * @param exception
     */
    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
        log.info("custom producer interceptor");
        if (exception == null) {
            successCounter.incrementAndGet();
        } else {
            errorCounter.incrementAndGet();
        }
    }

    @Override
    public void close() {
        log.info("Successful sent: {}， Failed sent: {}", successCounter.get(), errorCounter.get());
    }

    @Override
    public void configure(Map<String, ?> configs) {
        errorCounter = new AtomicInteger(0);
        successCounter = new AtomicInteger(0);
    }
}
