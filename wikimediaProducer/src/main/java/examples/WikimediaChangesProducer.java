package examples;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.sse.EventSourceListener;
import okhttp3.sse.EventSources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class WikimediaChangesProducer implements CommandLineRunner {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final OkHttpClient client;

    @Autowired
    public WikimediaChangesProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.client = new OkHttpClient();
    }

    @Override
    public void run(String... args) throws Exception {
        String topic = "wikimedia.recentchange";
        String url = "https://stream.wikimedia.org/v2/stream/recentchange";

        // Build the HTTP request
        Request request = new Request.Builder()
                .url(url)
                .build();

        // Create a custom EventSourceListener
        EventSourceListener listener = new WikimediaEventSourceListener(kafkaTemplate, topic);

        // Use OkHttp's EventSources to connect and listen to the stream
        EventSources.createFactory(client)
                .newEventSource(request, listener);

        // Keep the producer running for 10 minutes
        TimeUnit.MINUTES.sleep(10);
    }
}
