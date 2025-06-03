package com.manumarcos.springcloud.authservice.exception;

import feign.FeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalFeignExceptionHandler {

    @ExceptionHandler(FeignException.BadRequest.class)
    public ResponseEntity<String> handleFeignBadRequest(FeignException.BadRequest ex){
        return ResponseEntity.badRequest().body(ex.contentUTF8());
    }
}
