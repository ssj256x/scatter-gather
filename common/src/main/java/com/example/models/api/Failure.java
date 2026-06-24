package com.example.models.api;

import lombok.Builder;

@Builder
public record Failure(String id, String reason) {
}
