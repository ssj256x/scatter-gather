package com.example.scatterer.api;

import com.example.models.api.BatchRequest;
import com.example.models.api.BatchResponse;
import com.example.scatterer.kafka.producers.SimpleProducer;
import com.example.scatterer.models.dto.BatchRequestDTO;
import com.example.scatterer.services.RandomIdService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@Log4j2
@RestController
@RequestMapping(
        value = "/api/v1/scatter",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class ApiController {

    private final SimpleProducer simpleProducer;
    private final RandomIdService randomIdService;

    @PostMapping("create")
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
