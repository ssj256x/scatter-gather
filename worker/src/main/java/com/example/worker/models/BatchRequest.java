package com.example.worker.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@RequiredArgsConstructor
public class BatchRequest {
    private final String activity;
    private final List<String> ids;
}
