package ar.com.manumarcos.kahootclone.microservices.quiz_service.service;

import ar.com.manumarcos.kahootclone.microservices.quiz_service.dto.request.QuizRequestDTO;
import ar.com.manumarcos.microservices.commons.dto.quiz.QuizResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IQuizService {

    Page<QuizResponseDTO> getAll(Pageable pageable);
    QuizResponseDTO findById(String quizID);
    void save(QuizRequestDTO quizRequestDTO);
    void delete(String id);

}
