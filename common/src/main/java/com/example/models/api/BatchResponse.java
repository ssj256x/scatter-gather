package com.example.models.api;

import lombok.Builder;

import java.util.UUID;

@Builder
public record BatchResponse(UUID requestId, String activity) {
}
