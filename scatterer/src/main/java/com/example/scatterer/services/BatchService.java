package com.example.scatterer.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class BatchService {

    @Value("${scatter.batchSize}")
    private int batchSize = 5;

    public List<List<String>> createBatches(List<String> ids) {

        int size = ids.size();
        List<List<String>> batches = new ArrayList<>();

        for (int i = 0; i < size; i += batchSize) {
            List<String> currentBatch = new ArrayList<>();
            for (int j = i; j < i + batchSize && j < size; j++) {
                currentBatch.add(ids.get(j));
            }
            batches.add(currentBatch);
        }
        return batches;
    }
}
