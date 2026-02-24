package com.example.gatherer.models;

import lombok.*;

@Getter
@Setter
public class AggregatedResult {
    private String requestId;
    private int total;
    private int passed;
    private int failed;
    private int batchesReceived;
    private int totalBatches;
    private String status; // IN_PROGRESS / COMPLETED
}
