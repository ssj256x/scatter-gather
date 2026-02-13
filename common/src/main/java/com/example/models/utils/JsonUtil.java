package com.example.models.utils;

import com.example.models.api.BatchRequest;
import com.example.models.events.BatchEvent;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.SneakyThrows;

public class JsonUtil {
    @Getter
    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
    }

    @SneakyThrows
    public static <T> String toJson(T obj) {
        return objectMapper.writeValueAsString(obj);
    }

    @SneakyThrows
    public static <T> T fromJson(String str, Class<T> clazz) {
        return objectMapper.readValue(str, clazz);
    }

    @SneakyThrows
    public static <T> T fromJson(String str, TypeReference<T> clazz) {
        return objectMapper.readValue(str, clazz);
    }

    @SneakyThrows
    public static <T> T fromJson(String str, JavaType javaType) {
        return objectMapper.readValue(str, javaType);
    }
}

