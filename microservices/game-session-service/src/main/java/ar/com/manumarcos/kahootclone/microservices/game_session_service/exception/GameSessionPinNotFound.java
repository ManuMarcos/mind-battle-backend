package ar.com.manumarcos.kahootclone.microservices.game_session_service.exception;

public class GameSessionPinNotFound extends RuntimeException {
    public GameSessionPinNotFound(String pin) {
        super("Session with pin: " + pin + " not found.");
    }
}
