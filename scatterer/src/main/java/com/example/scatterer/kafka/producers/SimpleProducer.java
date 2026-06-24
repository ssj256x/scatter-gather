package com.example.scatterer.kafka.producers;

import com.example.models.api.BatchRequest;
import com.example.models.events.BatchEvent;
import com.example.models.utils.JsonUtil;
import com.example.scatterer.Constants;
import com.example.scatterer.models.dto.BatchRequestDTO;
import com.example.scatterer.services.BatchService;
import com.example.scatterer.services.RandomIdService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@RequiredArgsConstructor
public class SimpleProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final BatchService batchService;
    private final RandomIdService randomIdService;

    public void publish(BatchRequestDTO batchRequest) {
        var batches = batchService.createBatches(batchRequest.getIds());
        var requestId = "REQ_" + randomIdService.generateRandomId();

        for (int i = 0; i < batches.size(); i++) {
            var eventId = "EV_" + randomIdService.generateRandomId();
            var batchId = "BCH_" + randomIdService.generateRandomId();

            var event = BatchEvent.builder()
                    .eventId(eventId)
                    .eventType("BATCH_REQUEST")
                    .requestId(requestId)
                    .batchId(batchId)
                    .batchNumber(i + 1)
                    .totalBatches(batches.size())
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .payload(new BatchRequest(batchRequest.getActivity(), batches.get(i)))
                    .build();

            kafkaTemplate.send(
                    new ProducerRecord<>(
                            Constants.TOPIC_NAME,
                            requestId + ":" + batchId,
                            JsonUtil.toJson(event)
                    )
            );
        }
    }
}
