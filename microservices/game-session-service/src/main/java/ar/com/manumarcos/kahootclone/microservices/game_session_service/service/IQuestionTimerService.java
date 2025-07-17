package ar.com.manumarcos.kahootclone.microservices.game_session_service.service;

public interface IQuestionTimerService {

    void startTimer(String gameSessionId, int timeLimitSeconds);
}
