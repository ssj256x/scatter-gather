package com.example.worker.services;

import com.example.models.api.BatchRequest;
import com.example.models.api.BatchResult;
import com.example.models.api.Failure;
import com.example.models.api.Success;
import com.example.worker.util.RandomSuccessErrorUtil;
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
        List<Success> successes = new ArrayList<>();

        for (var id : batchRequest.getIds()) {
            var result = processItem(id);

            if (result.isRight()) {
                passed++;
                successes.add(result.get());
            } else {
                failed++;
                failures.add(result.getLeft());
            }
        }

        return BatchResult.builder()
                .total(batchRequest.getIds().size())
                .passed(passed)
                .failed(failed)
                .failures(failures)
                .successes(successes)
                .build();
    }

    public Either<Failure, Success> processItem(String id) {
        return random80True() ?
                Either.left(new Failure(id, RandomSuccessErrorUtil.randomErrorMessage())) :
                Either.right(new Success(id, RandomSuccessErrorUtil.randomSuccessMessage()));
    }

    private static boolean random80True() {
        return ThreadLocalRandom.current().nextDouble() > 0.8;
    }
}
