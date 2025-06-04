package ar.com.manumarcos.kahootclone.microservices.game_session_service.mapper.quiz;


import ar.com.manumarcos.kahootclone.microservices.game_session_service.model.EmbeddedQuiz;
import ar.com.manumarcos.microservices.commons.dto.quiz.QuizResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {IQuestionMapper.class})
public interface IQuizMapper {
    QuizResponseDTO toDTO(EmbeddedQuiz quiz);
    EmbeddedQuiz toEntity(QuizResponseDTO quizResponseDTO);
}
