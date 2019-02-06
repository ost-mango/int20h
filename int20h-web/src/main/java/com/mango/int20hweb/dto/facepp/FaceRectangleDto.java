package com.mango.int20hweb.dto.facepp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FaceRectangleDto {
    private Integer width;
    private Integer top;
    private Integer left;
    private Integer height;
}
