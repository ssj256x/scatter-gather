package com.example.worker.models;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class BatchResult {
    private final Integer total;
    private final Integer passed;
    private final Integer failed;
    private final List<Failure> failures;
}
