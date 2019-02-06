package com.mango.int20hweb.dto.facepp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetectFaceResponseDto {

    @JsonProperty("image_id")
    private String imageId;

    @JsonProperty("request_id")
    private String requstId;

    @JsonProperty("time_used")
    private Long timeUsed;

    @JsonProperty("faces")
    private List<FaceDto> faceDtoList;
}
