package ar.com.manumarcos.kahootclone.microservices.quiz_service.mapper;

import ar.com.manumarcos.kahootclone.microservices.quiz_service.dto.request.OptionRequestDTO;
import ar.com.manumarcos.kahootclone.microservices.quiz_service.model.EmbeddedOption;
import ar.com.manumarcos.microservices.commons.dto.quiz.OptionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IOptionMapper {
    EmbeddedOption toEntity(OptionRequestDTO optionRequestDTO);
    OptionDTO toDTO(EmbeddedOption embeddedOption);
}
