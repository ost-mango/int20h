package com.mango.int20hweb.dto.facepp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FaceDto {

    @JsonProperty("face_rectangle")
    private FaceRectangleDto faceRectangleDto;

    @JsonProperty("face_token")
    private String faceToken;

    @JsonProperty("attributes")
    private FaceAttributesDto attributes;

}
