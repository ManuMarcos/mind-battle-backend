package ar.com.manumarcos.kahootclone.microservices.game_session_service.exception;

public class UserNotFoundInSessionException extends RuntimeException {

    public UserNotFoundInSessionException(String username, String sessionId ) {
        super("User with username: " + username + " not found in session with id: " + sessionId);
    }
}
