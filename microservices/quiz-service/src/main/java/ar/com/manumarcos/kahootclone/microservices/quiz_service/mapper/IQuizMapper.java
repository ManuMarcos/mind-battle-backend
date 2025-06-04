package ar.com.manumarcos.kahootclone.microservices.quiz_service.mapper;

import ar.com.manumarcos.kahootclone.microservices.quiz_service.dto.QuizDTO;
import ar.com.manumarcos.kahootclone.microservices.quiz_service.model.Quiz;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {IQuestionMapper.class, IOptionMapper.class})
public interface IQuizMapper {
    Quiz toEntity(QuizDTO quizDTO);
    QuizDTO toDTO(Quiz quiz);
}
