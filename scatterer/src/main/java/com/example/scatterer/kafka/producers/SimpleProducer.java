package com.example.scatterer.kafka.producers;

import com.example.scatterer.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SimpleProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void publish(String msg) {
        kafkaTemplate.send(Constants.TOPIC_NAME, msg);
    }
}
