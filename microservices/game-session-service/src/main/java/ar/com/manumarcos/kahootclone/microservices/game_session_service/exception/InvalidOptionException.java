package ar.com.manumarcos.kahootclone.microservices.game_session_service.exception;

public class InvalidOptionException extends RuntimeException {
    public InvalidOptionException(String optionId, String questionId, String sessionId) {
        super(String.format("The option with id: %s does not exist in " +
                "question with id: %s and session with id: %s", optionId, questionId, sessionId));
    }
}
