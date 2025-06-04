package ar.com.manumarcos.kahootclone.user.exception;

import ar.com.manumarcos.microservices.commons.exception.ErrorResponse;
import ar.com.manumarcos.microservices.commons.exception.GlobalExceptionHandler;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
@Primary
public class UserExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex){
        var errors = new HashMap<String, String>();
        var fieldName = "user";
        errors.put(fieldName, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(errors));
    }

    @ExceptionHandler(UsernameAlreadyExists.class)
    public ResponseEntity<ErrorResponse> handleUsernameAlreadyExists(UsernameAlreadyExists ex){
        var errors = new HashMap<String, String>();
        var fieldName = "user";
        errors.put(fieldName, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(errors));
    }

}
