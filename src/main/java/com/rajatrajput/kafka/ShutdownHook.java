package com.rajatrajput.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ShutdownHook implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(ShutdownHook.class);

    @Autowired
    private ConsumerServiceForShutdown consumerService;

    @Override
    public void run(String... args) throws Exception {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Detected a shutdown, let's exit by calling consumer.wakeup()...");
            consumerService.shutdown();
        }));
    }
}
