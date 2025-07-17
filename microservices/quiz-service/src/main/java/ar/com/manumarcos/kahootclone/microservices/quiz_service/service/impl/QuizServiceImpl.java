package ar.com.manumarcos.kahootclone.microservices.quiz_service.service.impl;

import ar.com.manumarcos.kahootclone.microservices.quiz_service.dto.request.QuizRequestDTO;
import ar.com.manumarcos.kahootclone.microservices.quiz_service.exception.QuizNotFoundException;
import ar.com.manumarcos.kahootclone.microservices.quiz_service.mapper.IQuizMapper;
import ar.com.manumarcos.kahootclone.microservices.quiz_service.model.Quiz;
import ar.com.manumarcos.kahootclone.microservices.quiz_service.repository.IQuizRepository;
import ar.com.manumarcos.kahootclone.microservices.quiz_service.service.IQuizService;
import ar.com.manumarcos.microservices.commons.dto.quiz.QuizResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements IQuizService {

    private final IQuizRepository quizRepository;
    private final IQuizMapper quizMapper;

    @Override
    public Page<QuizResponseDTO> getAll(Pageable pageable) {
        Page<Quiz> quizzes = quizRepository.findAll(pageable);
        return quizzes.map(quizMapper::toDTO);
    }

    @Override
    public QuizResponseDTO findById(String quizID) {
        Quiz quiz = quizRepository.findById(quizID).orElseThrow(
                () -> new QuizNotFoundException(quizID)
        );
        return quizMapper.toDTO(quiz);
    }

    @Override
    public void save(QuizRequestDTO quizRequestDTO) {
        Quiz quiz = quizMapper.toEntity(quizRequestDTO);
        quiz.getQuestions().forEach((question) -> {
            AtomicInteger index = new AtomicInteger();
            question.setId(UUID.randomUUID().toString());
            question.getOptions().forEach(
                    (option) -> {
                        option.setId(UUID.randomUUID().toString());
                        option.setOrder(index.getAndIncrement());
                    }
            );
        });
        quizRepository.save(quiz);
    }

    @Override
    public void delete(String id) {
        if(quizRepository.findById(id).isPresent()){
            quizRepository.deleteById(id);
        }
        else{
            throw new QuizNotFoundException(id);
        }
    }
}
