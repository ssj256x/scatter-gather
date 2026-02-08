package com.example.scatterer.api.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class BatchResponse {
    private final String status;
    private final Integer total;
    private final Integer passed;
    private final Integer failed;
}
