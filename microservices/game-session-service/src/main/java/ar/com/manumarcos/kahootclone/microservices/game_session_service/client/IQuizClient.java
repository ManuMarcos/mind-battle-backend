package ar.com.manumarcos.kahootclone.microservices.game_session_service.client;


import ar.com.manumarcos.microservices.commons.dto.quiz.QuizResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "quiz-service", path = "/api/quizzes")
public interface IQuizClient {

    @GetMapping("/{quizId}")
    QuizResponseDTO getQuizById(@PathVariable String quizId);

}
