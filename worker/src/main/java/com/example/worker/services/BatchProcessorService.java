package com.example.worker.services;

import com.example.worker.models.BatchRequest;
import com.example.worker.models.BatchResult;
import com.example.worker.models.Failure;
import com.example.worker.util.RandomErrorUtil;
import io.vavr.control.Either;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class BatchProcessorService {

    public BatchResult processBatch(BatchRequest batchRequest) {
        int passed = 0;
        int failed = 0;
        List<Failure> failures = new ArrayList<>();

        for (var id : batchRequest.getIds()) {
            var result = processItem(id);

            if (result.isRight()) {
                passed++;
                continue;
            }

            failed++;
            failures.add(result.getLeft());
        }

        return BatchResult.builder()
                .total(batchRequest.getIds().size())
                .passed(passed)
                .failed(failed)
                .failures(failures)
                .build();
    }

    public Either<Failure, Boolean> processItem(String id) {
        return random80True() ?
                Either.left(new Failure(id, RandomErrorUtil.randomErrorMessage())) :
                Either.right(true);
    }

    private static boolean random80True() {
        return ThreadLocalRandom.current().nextDouble() < 0.8;
    }
}
