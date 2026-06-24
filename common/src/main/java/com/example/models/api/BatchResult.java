package com.example.models.api;

import lombok.*;

import java.util.List;

@Builder
public record BatchResult(
        Integer total,
        Integer passed,
        Integer failed,
        List<Failure> failures,
        List<Success> successes) {
}
