package com.mango.int20hweb.config.flickr;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Data
@Configuration
@ConfigurationProperties("flickr")
public class FlickrApiProperties {

    private String apiKey;
    private String apiSecret;
    private Map<String,List<String>> photosetIdListByUserId;
    private String[] tagList;
    private String outputPhotoDir;
}
