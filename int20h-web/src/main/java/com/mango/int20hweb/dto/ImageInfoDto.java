package com.mango.int20hweb.dto;

import com.mango.int20hweb.domain.enums.Emotion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageInfoDto {
    private String id;
    private Emotion emotion;
    private Integer thumbnailHeight;
    private Integer thumbnailWidth;
}
