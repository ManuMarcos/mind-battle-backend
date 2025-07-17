package ar.com.manumarcos.kahootclone.microservices.game_session_service.service;

import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.response.QuestionDto;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.ws.AnswerReceivedResponseDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.ws.PlayerDTO;

public interface IWebSocketNotificationService {
    void notifySessionStarted(String gameSessionId, QuestionDto questionDto);
    void notifyNextQuestion(String gameSessionId, QuestionDto questionDto);
    void notifyPlayerJoined(String gameSessionId, PlayerDTO playerDTO);
    void notifyAnswerReceived(String gameSessionId, AnswerReceivedResponseDTO answerReceivedResponseDTO);
    void notifyQuestionEnd(String gameSessionId, String questionId);
    void notifyPlayerLeft(String gameSessionId, PlayerDTO playerDTO);
}
