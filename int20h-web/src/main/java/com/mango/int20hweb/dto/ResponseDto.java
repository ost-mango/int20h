package com.mango.int20hweb.dto;

import lombok.Data;

@Data
public class ResponseDto<T> {

    private Boolean success;

    private T body;

    public ResponseDto(T body, Boolean success) {
        this.success = success;
        this.body = body;
    }

    public static <T> ResponseDto<T> of(T body) {
        return new ResponseDto<>(body, true);
    }

    public static <T> ResponseDto<T> error(T body) {
        return new ResponseDto<>(body, false);
    }
}
