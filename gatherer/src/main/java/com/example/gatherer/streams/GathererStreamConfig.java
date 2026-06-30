package com.example.gatherer.streams;

import com.example.gatherer.models.AggregatedResult;
import com.example.models.api.BatchResult;
import com.example.models.events.BatchEvent;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.support.serializer.JsonSerde;

@Configuration
@EnableKafkaStreams
public class GathererStreamConfig {

    @Bean
    public KStream<String, BatchEvent<BatchResult>> kStream(StreamsBuilder streamsBuilder) {

        JsonSerde<BatchEvent<BatchResult>> batchEventSerde = new JsonSerde<>(new TypeReference<>() {
        });
        batchEventSerde.deserializer().addTrustedPackages("*");

        JsonSerde<AggregatedResult> aggregatedResultSerde = new JsonSerde<>(AggregatedResult.class);
        aggregatedResultSerde.deserializer().addTrustedPackages("*");

        KStream<String, BatchEvent<BatchResult>> stream = streamsBuilder.stream(
                "batch-responses",
                Consumed.with(Serdes.String(), batchEventSerde)
        );

        stream.groupByKey()
                .aggregate(
                        AggregatedResult::new,
                        this::aggregator,
                        Materialized.<String, AggregatedResult, KeyValueStore<Bytes, byte[]>>as("aggregation-store")
                                .withKeySerde(Serdes.String())
                                .withValueSerde(aggregatedResultSerde)
                )
                .toStream()
                .filter((key, value) -> value.getStatus().equals("COMPLETED"))
                .to("batch-aggregated",
                        Produced.with(Serdes.String(), aggregatedResultSerde));

        return stream;
    }

    private AggregatedResult aggregator(String key,
                                        BatchEvent<BatchResult> value,
                                        AggregatedResult aggregate) {

        BatchResult batch = value.getPayload();

        aggregate.setRequestId(key);

        aggregate.setTotal(aggregate.getTotal() + batch.total());
        aggregate.setPassed(aggregate.getPassed() + batch.passed());
        aggregate.setFailed(aggregate.getFailed() + batch.failed());

        aggregate.setTotalBatches(value.getTotalBatches());
        aggregate.setBatchesReceived(aggregate.getBatchesReceived() + 1);

        // Merge success details
        if (batch.successes() != null) {
            aggregate.getSuccesses().addAll(batch.successes());
        }

        // Merge failure details
        if (batch.failures() != null) {
            aggregate.getFailures().addAll(batch.failures());
        }

        aggregate.setStatus(
                aggregate.getBatchesReceived() == aggregate.getTotalBatches()
                        ? "COMPLETED"
                        : "IN_PROGRESS"
        );

        return aggregate;
    }
}
