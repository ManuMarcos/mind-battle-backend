package ar.com.manumarcos.kahootclone.microservices.game_session_service.mapper.gamesession;


import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.response.EmbeddedPlayerSessionResponseDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.model.EmbeddedPlayerSession;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {IPlayerAnswerMapper.class})
public interface IPlayerSessionMapper {
    EmbeddedPlayerSessionResponseDTO toDTO(EmbeddedPlayerSession embeddedPlayerSession);
}
