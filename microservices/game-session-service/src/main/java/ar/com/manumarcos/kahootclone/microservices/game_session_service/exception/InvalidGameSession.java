package ar.com.manumarcos.kahootclone.microservices.game_session_service.exception;

public class InvalidGameSession extends RuntimeException {
    public InvalidGameSession(String gameSessionId, String status) {
        super("The status of the session:" + gameSessionId + " must be " + status);
    }
}
