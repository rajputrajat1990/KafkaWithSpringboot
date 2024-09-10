package com.rajatrajput.kafka;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class KafkaProducerDemoApplication implements CommandLineRunner {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Autowired
    private ConfigurableApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(KafkaProducerDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        kafkaProducerService.sendMessage("damn it");

        // Close the Spring context to stop the application
        SpringApplication.exit(context, () -> 0);
    }
}
