package ar.com.manumarcos.kahootclone.microservices.game_session_service.exception;

public class GameSessionInvalid extends RuntimeException {
    public GameSessionInvalid(String gameSessionId) {
        super("The status of the session:" + gameSessionId + " must be CREATED to start");
    }
}
