package ar.com.manumarcos.kahootclone.microservices.game_session_service.mapper.quiz;

import ar.com.manumarcos.kahootclone.microservices.game_session_service.model.EmbeddedQuestion;
import ar.com.manumarcos.microservices.commons.dto.quiz.QuestionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {IOptionMapper.class})
public interface IQuestionMapper {
    QuestionDTO toDTO(EmbeddedQuestion embeddedQuestion);
    EmbeddedQuestion toEntity(QuestionDTO questionDTO);
}
