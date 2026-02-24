package com.example.models.api;

import lombok.*;

import java.util.List;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class BatchResult {
    private final Integer total;
    private final Integer passed;
    private final Integer failed;
    private final List<Failure> failures;
}
