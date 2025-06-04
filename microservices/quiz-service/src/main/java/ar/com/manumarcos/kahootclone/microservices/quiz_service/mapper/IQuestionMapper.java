package ar.com.manumarcos.kahootclone.microservices.quiz_service.mapper;

import ar.com.manumarcos.kahootclone.microservices.quiz_service.dto.request.QuestionDTO;
import ar.com.manumarcos.kahootclone.microservices.quiz_service.model.EmbeddedQuestion;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {IOptionMapper.class})
public interface IQuestionMapper {
    EmbeddedQuestion toEntity(QuestionDTO questionDTO);
    QuestionDTO toDTO(EmbeddedQuestion embeddedQuestion);
}
