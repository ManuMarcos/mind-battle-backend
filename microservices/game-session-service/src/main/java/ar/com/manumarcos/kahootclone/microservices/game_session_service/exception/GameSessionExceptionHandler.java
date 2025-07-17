package ar.com.manumarcos.kahootclone.microservices.game_session_service.exception;


import ar.com.manumarcos.microservices.commons.exception.ErrorResponse;
import ar.com.manumarcos.microservices.commons.exception.GlobalExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GameSessionExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(GameSessionNotFound.class)
    public ResponseEntity<ErrorResponse> gameSessionNotFoundHandler(GameSessionNotFound ex, HttpServletRequest request){
        String path = request.getRequestURI();
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                path
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(QuizNotFoundException.class)
    public ResponseEntity<ErrorResponse> quizNotFoundHandler(QuizNotFoundException ex, HttpServletRequest request){
        String path = request.getRequestURI();
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                path
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(UserNotFoundInSessionException.class)
    public ResponseEntity<ErrorResponse> userNotFoundInSessionHandle(UserNotFoundInSessionException ex,
                                                                     HttpServletRequest request){
        String path = request.getRequestURI();
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                path
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(NotGameHostException.class)
    public ResponseEntity<ErrorResponse> notGameHostHandler(NotGameHostException ex,
                                                            HttpServletRequest request){
        String path = request.getRequestURI();
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.FORBIDDEN.value(),
                HttpStatus.FORBIDDEN.getReasonPhrase(),
                ex.getMessage(),
                path
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }
}
