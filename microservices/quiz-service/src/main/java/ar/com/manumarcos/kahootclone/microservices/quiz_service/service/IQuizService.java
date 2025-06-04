package ar.com.manumarcos.kahootclone.microservices.quiz_service.service;

import ar.com.manumarcos.kahootclone.microservices.quiz_service.dto.QuizDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IQuizService {

    Page<QuizDTO> getAll(Pageable pageable);
    QuizDTO findById(String quizID);
    void save(QuizDTO quizDTO);
    void delete(String id);

}
