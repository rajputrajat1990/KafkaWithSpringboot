package com.rajatrajput.kafka;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaProducerKeyService {

    private static final Logger log = LoggerFactory.getLogger(KafkaProducerKeyService.class);

    private static final String TOPIC = "demo_java";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessageWithKey(String key, String message) {
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(TOPIC, key, message);

        // Handle success and failure using CompletableFuture callbacks
        future.thenAccept(result -> {
            RecordMetadata metadata = result.getRecordMetadata();
            log.info("Key: {} | Partition: {}", key, metadata.partition());
        }).exceptionally(ex -> {
            log.error("Error while producing", ex);
            return null;
        });
    }

    public void sendMultipleMessages() throws InterruptedException {
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 10; i++) {
                String key = "id_" + i;
                String value = "hello world " + i;
                sendMessageWithKey(key, value);
            }

            // Wait for 500ms between batches
            Thread.sleep(500);
        }
    }
}
