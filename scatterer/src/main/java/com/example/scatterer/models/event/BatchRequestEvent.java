package com.example.scatterer.models.event;

import com.example.scatterer.api.requests.BatchRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Builder
@ToString
public class BatchRequestEvent {
    private final String eventId;
    private final String eventType = "BATCH_REQUEST";
    private final String requestId;
    private final String batchId;
    private final Integer batchNumber;
    private final Integer totalBatches;
    private final Timestamp timestamp;
    private final BatchRequest payload;
}
