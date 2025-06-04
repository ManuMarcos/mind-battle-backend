package ar.com.manumarcos.kahootclone.microservices.game_session_service.exception;

public class QuizNotFoundException extends RuntimeException {
    public QuizNotFoundException(String quizId) {
        super("Quiz with id: " + quizId + " not found.");
    }
}
