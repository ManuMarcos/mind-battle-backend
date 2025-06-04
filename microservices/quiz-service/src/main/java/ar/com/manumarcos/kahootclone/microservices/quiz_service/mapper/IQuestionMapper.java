package ar.com.manumarcos.kahootclone.microservices.quiz_service.mapper;

import ar.com.manumarcos.kahootclone.microservices.quiz_service.dto.QuestionDTO;
import ar.com.manumarcos.kahootclone.microservices.quiz_service.model.Question;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {IOptionMapper.class})
public interface IQuestionMapper {
    Question toEntity(QuestionDTO questionDTO);
    QuestionDTO toDTO(Question question);
}
