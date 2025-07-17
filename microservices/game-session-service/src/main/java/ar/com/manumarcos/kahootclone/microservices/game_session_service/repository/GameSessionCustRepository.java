package ar.com.manumarcos.kahootclone.microservices.game_session_service.repository;

import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.websocket.AnswerStatsResponseDTO;

import java.util.List;

public interface GameSessionCustRepository {
    List<AnswerStatsResponseDTO> getStatsForQuestion(String gameSessionId, String questionId);
}
