package ar.com.manumarcos.kahootclone.microservices.game_session_service.mapper.gamesession;


import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.response.EmbeddedPlayerAnswerResponseDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.mapper.quiz.IOptionMapper;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.model.EmbeddedPlayerAnswer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {IOptionMapper.class})
public interface IPlayerAnswerMapper {
    EmbeddedPlayerAnswerResponseDTO toDTO(EmbeddedPlayerAnswer embeddedPlayerAnswer);
}
