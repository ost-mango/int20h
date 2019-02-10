package com.mango.int20hweb.domain;

import com.mango.int20hweb.domain.enums.Emotion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageTuple {
    private File original;
    private File thumbnail;
    private Emotion emotion;

}
