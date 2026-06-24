package com.example.models.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Builder
@ToString
public class BatchEvent<T> {
    private final String eventId;
    private final String eventType;
    private final String requestId;
    private final String batchId;
    private final Integer batchNumber;
    private final Integer totalBatches;
    private final Timestamp timestamp;
    private final T payload;

    @JsonCreator
    public BatchEvent(
            @JsonProperty("eventId") String eventId,
            @JsonProperty("eventType") String eventType,
            @JsonProperty("requestId") String requestId,
            @JsonProperty("batchId") String batchId,
            @JsonProperty("batchNumber") Integer batchNumber,
            @JsonProperty("totalBatches") Integer totalBatches,
            @JsonProperty("timestamp") Timestamp timestamp,
            @JsonProperty("payload") T payload
    ) {
        this.eventId = eventId;
        this.eventType = eventType;
        this.requestId = requestId;
        this.batchId = batchId;
        this.batchNumber = batchNumber;
        this.totalBatches = totalBatches;
        this.timestamp = timestamp;
        this.payload = payload;
    }
}
