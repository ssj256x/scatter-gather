package com.example.worker.util;

import java.util.concurrent.ThreadLocalRandom;

public class RandomErrorUtil {
    private static final String[] ERROR_MESSAGES = {
            "Database connection timeout",
            "Invalid configuration format",
            "Authentication failed",
            "Resource not found",
            "Upstream service unavailable",
            "Request validation failed",
            "Transaction rollback occurred",
            "Disk I/O error",
            "Cache miss under high load",
            "Message serialization failed",
            "Kafka broker not reachable",
            "Permission denied",
            "Rate limit exceeded",
            "Dependency service timeout",
            "Corrupted input data"
    };

    public static String randomErrorMessage() {
        int index = ThreadLocalRandom.current().nextInt(ERROR_MESSAGES.length);
        return ERROR_MESSAGES[index];
    }
}
