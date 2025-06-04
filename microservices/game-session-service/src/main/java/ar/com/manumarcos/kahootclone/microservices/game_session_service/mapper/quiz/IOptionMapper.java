package ar.com.manumarcos.kahootclone.microservices.game_session_service.mapper.quiz;


import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.response.EmbeddedOptionResponseDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.model.EmbeddedOption;
import ar.com.manumarcos.microservices.commons.dto.quiz.OptionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IOptionMapper {
    EmbeddedOptionResponseDTO toDTO(EmbeddedOption option);
}
