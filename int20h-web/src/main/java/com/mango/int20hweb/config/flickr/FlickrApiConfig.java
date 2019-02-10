package com.mango.int20hweb.config.flickr;


import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.REST;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlickrApiConfig {

    @Bean
    @Autowired
    public Flickr flickr(FlickrApiProperties flickrApiProperties){
        return new Flickr(flickrApiProperties.getApiKey(), flickrApiProperties.getApiSecret(), new REST());
    }
}
