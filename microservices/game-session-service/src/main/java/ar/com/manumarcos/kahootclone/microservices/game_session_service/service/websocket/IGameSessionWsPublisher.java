package ar.com.manumarcos.kahootclone.microservices.game_session_service.service.websocket;

import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.response.QuestionDto;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.websocket.AnswerReceivedResponseDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.websocket.PlayerDTO;

public interface IGameSessionWsPublisher {
    void publishSessionStarted(String gameSessionId, QuestionDto questionDto);
    void publishNextQuestion(String gameSessionId, QuestionDto questionDto);
    void publishPlayerJoined(String gameSessionId, PlayerDTO playerDTO);
    void publishAnswerReceived(String gameSessionId, AnswerReceivedResponseDTO answerReceivedResponseDTO);
    void publishQuestionEnd(String gameSessionId, String questionId);
    void publishPlayerLeft(String gameSessionId, PlayerDTO playerDTO);
}
