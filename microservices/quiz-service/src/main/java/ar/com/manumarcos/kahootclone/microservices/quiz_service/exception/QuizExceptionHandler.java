package ar.com.manumarcos.kahootclone.microservices.quiz_service.exception;


import ar.com.manumarcos.microservices.commons.exception.ErrorResponse;
import ar.com.manumarcos.microservices.commons.exception.GlobalExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class QuizExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(QuizNotFoundException.class)
    public ResponseEntity<ErrorResponse> quizNotFoundExceptionHandler(QuizNotFoundException exception,
                                                                      HttpServletRequest request){
        String path = request.getRequestURI();
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                exception.getMessage(),
                path
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }


}
