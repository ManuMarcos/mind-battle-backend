package ar.com.manumarcos.kahootclone.microservices.game_session_service.exception;


import ar.com.manumarcos.microservices.commons.exception.ErrorResponse;
import ar.com.manumarcos.microservices.commons.exception.GlobalExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GameSessionExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(GameSessionNotFound.class)
    public ResponseEntity<ErrorResponse> gameSessionNotFoundHandler(GameSessionNotFound ex){
        var errors = new HashMap<String, String>();
        var fieldName = "gamesession";
        errors.put(fieldName, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(errors));
    }

    @ExceptionHandler(QuizNotFoundException.class)
    public ResponseEntity<ErrorResponse> quizNotFoundHandler(QuizNotFoundException ex){
        var errors = new HashMap<String, String>();
        var fieldName = "gamesession";
        errors.put(fieldName, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(errors));
    }

}
