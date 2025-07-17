package ar.com.manumarcos.kahootclone.microservices.quiz_service.controller;


import ar.com.manumarcos.kahootclone.microservices.quiz_service.controller.swagger.IQuizControllerSwagger;
import ar.com.manumarcos.kahootclone.microservices.quiz_service.dto.request.QuizRequestDTO;
import ar.com.manumarcos.kahootclone.microservices.quiz_service.service.IQuizService;
import ar.com.manumarcos.microservices.commons.dto.quiz.QuizResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
public class QuizController implements IQuizControllerSwagger {

    private final IQuizService quizService;

    @GetMapping
    public ResponseEntity<Page<QuizResponseDTO>> getAll(Pageable pageable){
        return ResponseEntity.ok( quizService.getAll(pageable));
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody QuizRequestDTO quizRequestDTO){
        quizService.save(quizRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Quiz created successfully");
    }

    @GetMapping("/{quizId}")
    public ResponseEntity<QuizResponseDTO> findById(@PathVariable String quizId){
        return ResponseEntity.ok( quizService.findById(quizId));
    }

    @DeleteMapping("/{quizId}")
    public ResponseEntity<String> deleteById(@PathVariable String quizId){
        quizService.delete(quizId);
        return ResponseEntity.ok("Quiz deleted successfully");
    }
}

