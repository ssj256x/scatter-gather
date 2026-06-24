package com.example.models.api;

import lombok.Builder;

@Builder
public record Success(String id, String message) {
}
