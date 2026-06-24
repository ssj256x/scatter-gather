package com.example.worker.util;

import java.util.concurrent.ThreadLocalRandom;

public class RandomSuccessErrorUtil {
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

    private static final String[] SUCCESS_MESSAGES = {
            "Worker execution successful",
            "Task processed successfully",
            "Item validated successfully",
            "Operation completed",
            "Record processed and stored",
            "Validation passed",
            "Execution successful",
            "Item processed",
            "Data transformation successful",
            "Worker task completed",
            "Processing completed without errors",
            "Result generated successfully",
            "Task finalized",
            "Operation executed successfully",
            "Processing successful",
            "Computation completed",
            "Work unit processed successfully",
            "Item handled successfully",
            "Execution finished successfully",
            "Task completed as expected"
    };

    public static String randomErrorMessage() {
        int index = ThreadLocalRandom.current().nextInt(ERROR_MESSAGES.length);
        return ERROR_MESSAGES[index];
    }

    public static String randomSuccessMessage() {
        int index = ThreadLocalRandom.current().nextInt(SUCCESS_MESSAGES.length);
        return SUCCESS_MESSAGES[index];
    }
}
