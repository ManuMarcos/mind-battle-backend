package ar.com.manumarcos.microservices.commons.exception;

import java.time.Instant;
import java.util.Map;

public record ErrorResponse(
        String timeStamp,
        int status,
        String error,
        String message,
        String path
) {
    public ErrorResponse(int status, String error, String message, String path){
        this(Instant.now().toString(), status, error, message, path);
    }
}
