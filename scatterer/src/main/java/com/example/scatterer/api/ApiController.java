package com.example.scatterer.api;

import com.example.scatterer.api.requests.BatchRequest;
import com.example.scatterer.api.requests.BatchResponse;
import com.example.scatterer.kafka.producers.SimpleProducer;
import com.example.scatterer.models.dto.BatchRequestDTO;
import com.example.scatterer.services.RandomIdService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Log4j2
@RestController
@RequiredArgsConstructor
public class ApiController {

    private final SimpleProducer simpleProducer;
    private final RandomIdService randomIdService;

    @PostMapping(
            value = "create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<BatchResponse> createScatter(@RequestBody BatchRequest request,
                                                       @RequestParam(value = "random-ids", required = false) boolean randomIds,
                                                       @RequestParam(required = false) int size) {

        log.info("Request : {}", request);

        BatchRequestDTO batchRequestDTO = new BatchRequestDTO(
                request.getActivity(),
                randomIds ? randomIdService.generateRandomIds(size) : request.getIds()
        );

        simpleProducer.publish(batchRequestDTO);

        return ResponseEntity.ok(
                BatchResponse.builder()
                        .requestId(UUID.randomUUID())
                        .activity(request.getActivity())
                        .build()
        );
    }
}
