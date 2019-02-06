package com.mango.int20hweb.controller;

import com.mango.int20hweb.dto.ResponseDto;
import com.mango.int20hweb.dto.error.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Throwable.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected ResponseDto<ErrorDto> handleGeneralException(Throwable ex, WebRequest request) {
        return ResponseDto.error(new ErrorDto(ex.getMessage()));
    }
}