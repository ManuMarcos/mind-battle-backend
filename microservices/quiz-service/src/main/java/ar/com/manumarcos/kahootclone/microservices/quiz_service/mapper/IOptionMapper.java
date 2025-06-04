package ar.com.manumarcos.kahootclone.microservices.quiz_service.mapper;

import ar.com.manumarcos.kahootclone.microservices.quiz_service.dto.request.OptionDTO;
import ar.com.manumarcos.kahootclone.microservices.quiz_service.model.EmbeddedOption;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IOptionMapper {
    EmbeddedOption toEntity(OptionDTO optionDTO);
    OptionDTO toDTO(EmbeddedOption embeddedOption);
}
