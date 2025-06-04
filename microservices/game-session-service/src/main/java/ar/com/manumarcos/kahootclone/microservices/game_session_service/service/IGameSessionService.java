package ar.com.manumarcos.kahootclone.microservices.game_session_service.service;

import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.request.GameSessionRequestDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.response.GameSessionResponseDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.websocket.dto.request.JoinRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IGameSessionService {

    Page<GameSessionResponseDTO> getAll(Pageable pageable);
    GameSessionResponseDTO save(GameSessionRequestDTO gameSessionRequestDTO);
    GameSessionResponseDTO findById(String gameSessionId);
    void deleteById(String gameSessionId);
    void joinGameSession(String pin, JoinRequestDTO joinRequestDTO);
}
