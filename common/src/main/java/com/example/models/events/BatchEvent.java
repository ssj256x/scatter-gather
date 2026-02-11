package com.example.models.events;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

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
}
