package ar.com.manumarcos.kahootclone.microservices.quiz_service.mapper;

import ar.com.manumarcos.kahootclone.microservices.quiz_service.dto.OptionDTO;
import ar.com.manumarcos.kahootclone.microservices.quiz_service.model.Option;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IOptionMapper {
    Option toEntity(OptionDTO optionDTO);
    OptionDTO toDTO(Option option);
}
