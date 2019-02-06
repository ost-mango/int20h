package com.mango.int20hweb.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.core.io.FileSystemResource;

@Data
@AllArgsConstructor
public class ImageTuple {
    public String id;
    public FileSystemResource image;
}
