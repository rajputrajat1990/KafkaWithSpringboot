package com.rajatrajput.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaProducerCallbackApplication implements CommandLineRunner {

    @Autowired
    private ProducerDemoWithCallbackService producerService;

    public static void main(String[] args) {
        SpringApplication.run(KafkaProducerCallbackApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        producerService.sendMessage("Hello Kafka with Spring Boot!");
    }
}
