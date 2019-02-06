package com.mango.int20hweb.service;

import com.mango.int20hweb.config.facepp.FaceppApiProperties;
import com.mango.int20hweb.domain.enums.Emotion;
import com.mango.int20hweb.dto.facepp.DetectFaceResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class FaceppService {

    private static final class RequestParam {
        private static final String IMAGE_FILE = "image_file";
        private static final String API_KEY = "api_key";
        private static final String API_SECRET = "api_secret";
        private static final String RETURN_ATTRIBUTES = "return_attributes";
    }

    private RestTemplate restTemplate;

    private FaceppApiProperties faceppApiProperties;

    public Emotion matchEmotion(FileSystemResource file) {
        DetectFaceResponseDto detectFaceResponseDto = analyseImage(file);
        Map<Emotion, Integer> emotionIntegerMap = new HashMap<>();
        Arrays.stream(Emotion.values()).forEach(emotion -> emotionIntegerMap.put(emotion, 0));

        detectFaceResponseDto.getFaceDtoList().stream()
                .map(faceDto -> faceDto.getAttributes().getEmotions().entrySet().stream()
                        .max(Comparator.comparingDouble(Map.Entry<Emotion, Double>::getValue))
                        .map(Map.Entry::getKey).orElse(Emotion.NEUTRAL))
                .forEach(emotion -> emotionIntegerMap.merge(emotion, 1, ((count1, count2) -> count1 + count2)));
        return emotionIntegerMap.entrySet().stream().max(Comparator.comparingInt(Map.Entry<Emotion, Integer>::getValue))
                .map(Map.Entry::getKey).orElse(Emotion.NEUTRAL);
    }

    private DetectFaceResponseDto analyseImage(FileSystemResource file) {
        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();
        body.add(RequestParam.IMAGE_FILE, file);

        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(body, null);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(faceppApiProperties.getApiDetectEndpoint())
                .queryParam(RequestParam.API_KEY, faceppApiProperties.getApiKey())
                .queryParam(RequestParam.API_SECRET, faceppApiProperties.getApiSecret())
                .queryParam(RequestParam.RETURN_ATTRIBUTES, "emotion");
        ResponseEntity<DetectFaceResponseDto> response = restTemplate
                .postForEntity(builder.toUriString(), requestEntity, DetectFaceResponseDto.class);
        return response.getBody();
    }

}
