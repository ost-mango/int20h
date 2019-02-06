package com.mango.int20hweb.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;

@Data
@AllArgsConstructor
public class ImageTuple {
    private File original;
    private File thumbnail;
    private Emotion emotion;

}
