package ar.com.manumarcos.kahootclone.microservices.game_session_service.exception;

public class InvalidQuestionException extends RuntimeException {
    public InvalidQuestionException(String questionId, String gameSessionId) {
        super(String.format("Question with id %s does not exist in session %s", questionId, gameSessionId));
    }
}
