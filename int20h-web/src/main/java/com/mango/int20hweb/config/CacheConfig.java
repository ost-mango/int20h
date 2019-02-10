package com.mango.int20hweb.config;

import com.mango.int20hweb.domain.ImageTuple;
import com.mango.int20hweb.domain.enums.Emotion;
import com.mango.int20hweb.dto.ImageInfoDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;

@Configuration
public class CacheConfig {
    @Bean
    public ConcurrentSkipListMap<Emotion, List<String>> imageRegistry() {
        ConcurrentSkipListMap<Emotion, List<String>> concurrentSkipListMap =  new ConcurrentSkipListMap<>();
        Arrays.stream(Emotion.values()).forEach(emotion -> concurrentSkipListMap.put(emotion, new LinkedList<>()));
        return concurrentSkipListMap;
    }

    @Bean
    public ConcurrentSkipListMap<String, ImageInfoDto> imageInfoRegistry() {
        return new ConcurrentSkipListMap<>();
    }

    @Bean
    public ConcurrentSkipListMap<String, ImageTuple> imageCache() {
        return new ConcurrentSkipListMap<>();
    }
}
