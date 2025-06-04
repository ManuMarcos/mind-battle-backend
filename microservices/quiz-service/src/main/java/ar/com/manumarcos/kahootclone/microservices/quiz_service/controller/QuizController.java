package ar.com.manumarcos.kahootclone.microservices.quiz_service.controller;


import ar.com.manumarcos.kahootclone.microservices.quiz_service.dto.QuizDTO;
import ar.com.manumarcos.kahootclone.microservices.quiz_service.model.Quiz;
import ar.com.manumarcos.kahootclone.microservices.quiz_service.service.IQuizService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
public class QuizController {

    private final IQuizService quizService;

    @GetMapping
    public ResponseEntity<Page<QuizDTO>> getAll(Pageable pageable){
        return ResponseEntity.ok( quizService.getAll(pageable));
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody QuizDTO quizDTO){
        quizService.save(quizDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Quiz created successfully");
    }

    @GetMapping("/{quizId}")
    public ResponseEntity<QuizDTO> findById(@PathVariable String quizId){
        return ResponseEntity.ok( quizService.findById(quizId));
    }

    @DeleteMapping("/{quizId}")
    public ResponseEntity<String> deleteById(@PathVariable String quizId){
        quizService.delete(quizId);
        return ResponseEntity.ok("Quiz deleted successfully");
    }
}

