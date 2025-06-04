package ar.com.manumarcos.kahootclone.microservices.quiz_service.exception;


import ar.com.manumarcos.microservices.common_exceptions.ErrorResponse;
import ar.com.manumarcos.microservices.common_exceptions.GlobalExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class QuizExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(QuizNotFoundException.class)
    public ResponseEntity<ErrorResponse> quizNotFoundExceptionHandler(QuizNotFoundException exception){
        var errors = new HashMap<String, String>();
        var fieldName = "quiz";
        errors.put(fieldName, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(errors));
    }


}
