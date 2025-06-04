package ar.com.manumarcos.kahootclone.microservices.game_session_service.mapper.gamesession;

import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.request.GameSessionRequestDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.response.GameSessionResponseDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.mapper.quiz.IQuizMapper;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.model.GameSession;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {IPlayerSessionMapper.class, IQuizMapper.class})
public interface IGameSessionMapper {
    GameSessionResponseDTO toDTO(GameSession gameSession);
    GameSession toEntity(GameSessionRequestDTO gameSessionRequestDTO);
}
