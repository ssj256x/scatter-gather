package com.example.gatherer.models;

import com.example.models.api.Failure;
import com.example.models.api.Success;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AggregatedResult {

    private String requestId;

    private int total;
    private int passed;
    private int failed;

    private int batchesReceived;
    private int totalBatches;

    private List<Success> successes = new ArrayList<>();
    private List<Failure> failures = new ArrayList<>();

    private String status; // IN_PROGRESS / COMPLETED
}
