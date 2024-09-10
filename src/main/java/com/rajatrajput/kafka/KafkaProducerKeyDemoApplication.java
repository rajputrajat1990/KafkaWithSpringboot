package com.rajatrajput.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class KafkaProducerKeyDemoApplication implements CommandLineRunner {

    @Autowired
    private KafkaProducerKeyService kafkaProducerKeyService;  // Updated service

    @Autowired
    private ConfigurableApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(KafkaProducerKeyDemoApplication.class, args);  // Updated class name
    }

    @Override
    public void run(String... args) throws Exception {
        kafkaProducerKeyService.sendMultipleMessages();

        // Close the application context to stop the app after the messages are sent
        SpringApplication.exit(context, () -> 0);
    }
}
