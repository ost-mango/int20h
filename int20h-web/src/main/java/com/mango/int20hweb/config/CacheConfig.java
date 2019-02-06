package com.mango.int20hweb.config;

import com.mango.int20hweb.domain.enums.Emotion;
import com.mango.int20hweb.domain.enums.ImageTuple;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;

@Configuration
public class CacheConfig {
    @Bean
    public ConcurrentSkipListMap<Emotion, List<ImageTuple>> imageCache() {
        return new ConcurrentSkipListMap<>();
    }
}
