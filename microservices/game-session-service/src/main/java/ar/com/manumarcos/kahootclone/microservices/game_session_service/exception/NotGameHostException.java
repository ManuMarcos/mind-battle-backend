package ar.com.manumarcos.kahootclone.microservices.game_session_service.exception;

public class NotGameHostException extends RuntimeException {
    public NotGameHostException(String userId, String gameSessionId) {
        super("User " + userId + " is not host of session " + gameSessionId);
    }
}
