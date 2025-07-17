package ar.com.manumarcos.kahootclone.microservices.game_session_service.service;

import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.request.AnswerRequestDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.request.GameSessionRequestDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.request.JoinRequestDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.request.SessionPlayerIdRequest;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.response.GameSessionResponseDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.response.QuestionDto;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.websocket.AnswerStatsResponseDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.model.SessionInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IGameSessionService {

    Page<GameSessionResponseDTO> getAll(Pageable pageable);
    GameSessionResponseDTO save(GameSessionRequestDTO gameSessionRequestDTO, String userId);
    GameSessionResponseDTO findById(String gameSessionId);
    void deleteById(String gameSessionId);
    GameSessionResponseDTO joinGameSession(String pin, JoinRequestDTO joinRequestDTO);
    void registerWebSocketSession(String gameSessionId, String sessionId, String username);
    void updateSessionPlayerId(String gameSessionId, String username , SessionPlayerIdRequest sessionPlayerIdRequest);
    void removePlayerFromSession(SessionInfo sessionInfo);
    boolean existsUserInGame(String gameSessionId, String username);
    QuestionDto start(String gameSessionId, String userId);
    QuestionDto nextQuestion(String gameSessionId, String userId);
    Long answer(String gameSessionId, AnswerRequestDTO answerRequestDTO);
    List<AnswerStatsResponseDTO> getQuestionStats(String gameSessionId, String questionId);
}
