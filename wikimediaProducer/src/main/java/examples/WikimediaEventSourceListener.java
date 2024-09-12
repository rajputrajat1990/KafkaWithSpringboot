package examples;

import okhttp3.Response;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

public class WikimediaEventSourceListener extends EventSourceListener {

    private static final Logger log = LoggerFactory.getLogger(WikimediaEventSourceListener.class);

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topic;

    public WikimediaEventSourceListener(KafkaTemplate<String, String> kafkaTemplate, String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @Override
    public void onOpen(EventSource eventSource, Response response) {
        log.info("Opened connection to stream");
    }

    @Override
    public void onEvent(EventSource eventSource, String id, String type, String data) {
        log.info("Received event: {}", data);
        // Send the event data to Kafka
        kafkaTemplate.send(topic, data);
    }

    @Override
    public void onClosed(EventSource eventSource) {
        log.info("Closed connection to stream");
    }

    @Override
    public void onFailure(EventSource eventSource, Throwable t, Response response) {
        log.error("Error in stream connection", t);
    }
}
