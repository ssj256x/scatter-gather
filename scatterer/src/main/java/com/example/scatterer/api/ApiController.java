package com.example.scatterer.api;

import com.example.scatterer.api.requests.BatchRequest;
import com.example.scatterer.api.requests.BatchResponse;
import com.example.scatterer.kafka.producers.SimpleProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
public class ApiController {

    private final SimpleProducer simpleProducer;

    @PostMapping(
            value = "create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<BatchResponse> createScatter(@RequestBody BatchRequest request) {

        log.info("Request : {}", request);
        simpleProducer.publish(request.getActivity());

        var resp = BatchResponse.builder()
                .status("COMPLETE")
                .total(10)
                .passed(10)
                .failed(0)
                .build();

        return ResponseEntity.ok(resp);
    }
}
