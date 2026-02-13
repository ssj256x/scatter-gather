package com.example.models.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class BatchRequest {
    private final String activity;
    private final List<String> ids;

    @JsonCreator
    public BatchRequest(
            @JsonProperty("activity") String activity,
            @JsonProperty("ids") List<String> ids
    ) {
        this.activity = activity;
        this.ids = ids;
    }

}
