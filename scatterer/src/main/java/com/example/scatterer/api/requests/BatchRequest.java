package com.example.scatterer.api.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class BatchRequest {
    private final String activity;
    private final List<Integer> ids;
}
