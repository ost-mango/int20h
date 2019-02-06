package com.mango.int20hweb.dto.error;

import lombok.Data;

@Data
public class ErrorDto {

    private String message;

    public ErrorDto(String message) {
        this.message = message;
    }
}