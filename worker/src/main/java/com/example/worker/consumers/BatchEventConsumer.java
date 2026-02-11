package com.example.worker.consumers;

import com.example.worker.Constants;
import com.example.worker.models.BatchRequest;
import com.example.worker.models.events.BatchEvent;
import com.example.worker.services.BatchProcessorService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BatchEventConsumer {

    private final BatchProcessorService batchProcessorService;

    @KafkaListener(
            topics = Constants.TOPIC_NAME,
            groupId = "batch-processor-group"
    )
    public void consume(ConsumerRecord<String, BatchEvent<BatchRequest>> event, Acknowledgment ack) {
        var batchEvent = event.value();
        var batchResult = batchProcessorService.processBatch(batchEvent.getPayload());

        BatchEvent.builder()
                .eventId("EV_" + UUID.randomUUID())
                .requestId(batchEvent.getRequestId())
                .batchId(batchEvent.getBatchId())
                .batchNumber(batchEvent.getBatchNumber())
                .totalBatches(batchEvent.getTotalBatches())
                .eventType("BATCH_RESULT")
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .payload(batchResult) // FIXME
                .build();

        ack.acknowledge();
    }
}
