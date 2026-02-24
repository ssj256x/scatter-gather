package com.example.worker.consumers;

import com.example.models.api.BatchRequest;
import com.example.models.api.BatchResult;
import com.example.models.utils.JsonUtil;
import com.example.worker.Constants;
import com.example.models.events.BatchEvent;
import com.example.worker.services.BatchProcessorService;
import com.fasterxml.jackson.databind.JavaType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class BatchEventConsumer {

    private final BatchProcessorService batchProcessorService;
    private final KafkaTemplate<String, BatchEvent<BatchResult>> kafkaTemplate;

    @KafkaListener(
            topics = Constants.TOPIC_NAME,
            groupId = "batch-processor-group"
    )
    public void consume(ConsumerRecord<String, String> event, Acknowledgment ack) {
        String batchEventJson = event.value();
        JavaType javaType = JsonUtil.getObjectMapper()
                .getTypeFactory()
                .constructParametricType(
                        BatchEvent.class,
                        BatchRequest.class
                );

        BatchEvent<BatchRequest> batchRequestEvent = JsonUtil.fromJson(
                batchEventJson,
                javaType
        );

        log.info("Consuming Batch Request : {}", batchRequestEvent);

        BatchResult batchResult = batchProcessorService.processBatch(batchRequestEvent.getPayload());

        BatchEvent<BatchResult> batchResultEvent = BatchEvent.<BatchResult>builder()
                .eventId("EV_" + UUID.randomUUID())
                .eventType("BATCH_RESULT")
                .requestId(batchRequestEvent.getRequestId())
                .batchId(batchRequestEvent.getBatchId())
                .batchNumber(batchRequestEvent.getBatchNumber())
                .totalBatches(batchRequestEvent.getTotalBatches())
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .payload(batchResult)
                .build();

        log.info("Publishing Batch Result : {}", batchResultEvent);

        kafkaTemplate.send(
                Constants.BATCH_RESPONSE_TOPIC,
                batchResultEvent.getRequestId(),
                batchResultEvent
        );

        ack.acknowledge();
    }
}
