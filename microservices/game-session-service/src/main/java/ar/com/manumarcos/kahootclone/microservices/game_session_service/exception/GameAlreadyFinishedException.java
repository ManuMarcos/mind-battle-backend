package ar.com.manumarcos.kahootclone.microservices.game_session_service.exception;

public class GameAlreadyFinishedException extends RuntimeException {
    public GameAlreadyFinishedException(String gameSessionId) {
        super("No more questions available for session: " + gameSessionId);
    }
}
