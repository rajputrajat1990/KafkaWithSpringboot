package com.rajatrajput.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    private static final Logger log = LoggerFactory.getLogger(ConsumerService.class);

    @KafkaListener(topics = "demo_java", groupId = "my-java-application")
    public void consume(ConsumerRecord<String, String> record) {
        log.info("Key: {}, Value: {}", record.key(), record.value());
        log.info("Partition: {}, Offset: {}", record.partition(), record.offset());
    }
}
