package com.example.scatterer.kafka.consumers;

import com.example.scatterer.Constants;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class SimpleConsumer {

    @KafkaListener(
            topics = Constants.TOPIC_NAME,
            groupId = "batch-processor-group"
    )
    public void consume(String message, Acknowledgment ack) {
        try {
            System.out.println("Received Message : " + message);
            ack.acknowledge();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
