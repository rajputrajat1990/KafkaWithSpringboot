package com.rajatrajput.kafka;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class ProducerDemoWithCallbackService {

    private static final Logger log = LoggerFactory.getLogger(ProducerDemoWithCallbackService.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${spring.kafka.template.default-topic}")
    private String topic;

    // Constructor injection of KafkaTemplate
    public ProducerDemoWithCallbackService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message) {
        log.info("Sending message: {}", message);

        // Creating a ProducerRecord with topic and message
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, message);

        // Sending message to Kafka
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(producerRecord);

        // Handling success or failure using CompletableFuture callbacks
        future.thenAccept(result -> {
            RecordMetadata metadata = result.getRecordMetadata();
            log.info("Message sent successfully \n" +
                    "Topic: " + metadata.topic() + "\n" +
                    "Partition: " + metadata.partition() + "\n" +
                    "Offset: " + metadata.offset() + "\n" +
                    "Timestamp: " + metadata.timestamp());
        }).exceptionally(ex -> {
            log.error("Error while producing message", ex);
            return null;
        });
    }
}
