package com.example.scatterer.models.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BatchRequestDTO {
    private String activity;
    private List<String> ids;
}
