package com.mango.int20hweb.dto.facepp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mango.int20hweb.domain.enums.Emotion;
import lombok.Data;

import java.util.Map;

@Data
public class FaceAttributesDto {

    @JsonProperty("emotion")
    private Map<Emotion, Double> emotions;
}
