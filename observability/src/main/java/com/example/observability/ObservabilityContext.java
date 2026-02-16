package com.example.observability;

import org.slf4j.MDC;

public class ObservabilityContext {

    public static void setRequest(String requestId) {
        MDC.put("requestId", requestId);
    }

    public static void setBatch(String batchId) {
        MDC.put("batchId", batchId);
    }

    public static void clear() {
        MDC.clear();
    }
}
