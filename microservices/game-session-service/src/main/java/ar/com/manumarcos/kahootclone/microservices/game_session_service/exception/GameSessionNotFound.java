package ar.com.manumarcos.kahootclone.microservices.game_session_service.exception;

public class GameSessionNotFound extends RuntimeException {
    public GameSessionNotFound(String gameSessionId) {
        super("Game session with id: " + gameSessionId + " not found.");
    }
}
