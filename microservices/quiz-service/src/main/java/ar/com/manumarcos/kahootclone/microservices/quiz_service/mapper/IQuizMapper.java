package ar.com.manumarcos.kahootclone.microservices.quiz_service.mapper;

import ar.com.manumarcos.kahootclone.microservices.quiz_service.dto.request.QuizRequestDTO;
import ar.com.manumarcos.kahootclone.microservices.quiz_service.model.Quiz;
import ar.com.manumarcos.microservices.commons.dto.quiz.QuizResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {IQuestionMapper.class})
public interface IQuizMapper {
    Quiz toEntity(QuizRequestDTO quizRequestDTO);
    QuizResponseDTO toDTO(Quiz quiz);
}
