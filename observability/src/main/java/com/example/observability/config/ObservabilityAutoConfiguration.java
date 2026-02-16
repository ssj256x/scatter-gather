package com.example.observability.config;

import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.config.MeterFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration(proxyBeanMethods = false)
public class ObservabilityAutoConfiguration {

    @Bean
    public MeterFilter commonTags() {
        Tag t1 = Tag.of("system", "system");
        Tag t2 = Tag.of("batch-platform", "batch-platform");
        return MeterFilter.commonTags(List.of(t1, t2));
    }
}
