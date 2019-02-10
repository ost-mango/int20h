package com.mango.int20hweb.config.facepp;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties("facepp")
public class FaceppApiProperties {
    private String apiKey;
    private String apiSecret;
    private String apiDetectEndpoint;
    private List<String> faceReturnAttributes;
}
