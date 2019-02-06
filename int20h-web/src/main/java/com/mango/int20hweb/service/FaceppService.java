package com.mango.int20hweb.service;

import com.mango.int20hweb.config.facepp.FaceppApiProperties;
import com.mango.int20hweb.dto.facepp.DetectFaceResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class FaceppService {

    private RestTemplate restTemplate;

    private FaceppApiProperties faceppApiProperties;

    public DetectFaceResponseDto analyseImage(File file) {
        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();
        body.add("image_file", file);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(body, headers);

        ResponseEntity<DetectFaceResponseDto> response = restTemplate
                .postForEntity(faceppApiProperties.getApiDetectEndpoint(), requestEntity, DetectFaceResponseDto.class);
        return response.getBody();
    }
}
