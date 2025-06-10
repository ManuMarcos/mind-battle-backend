package ar.com.manumarcos.kahootclone.microservices.game_session_service.service;

import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.request.GameSessionRequestDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.request.JoinRequestDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.request.SessionPlayerIdRequest;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.response.EmbeddedOptionResponseDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.response.EmbeddedQuestionResponseDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.response.GameSessionResponseDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.response.QuestionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IGameSessionService {

    Page<GameSessionResponseDTO> getAll(Pageable pageable);
    GameSessionResponseDTO save(GameSessionRequestDTO gameSessionRequestDTO);
    GameSessionResponseDTO findById(String gameSessionId);
    void deleteById(String gameSessionId);
    GameSessionResponseDTO joinGameSession(String pin, JoinRequestDTO joinRequestDTO);
    void updateSessionPlayerId(String gameSessionId, String username , SessionPlayerIdRequest sessionPlayerIdRequest);
    void removePlayerFromSession(String gameSessionId, String userSessionId);
    boolean existsUserInGame(String gameSessionId, String username);
    QuestionDto start(String gameSessionId);
}
