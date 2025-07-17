package ar.com.manumarcos.kahootclone.microservices.game_session_service.controller;

import ar.com.manumarcos.kahootclone.microservices.game_session_service.controller.swagger.IGameSessionControllerSwagger;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.request.GameSessionRequestDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.request.JoinRequestDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.request.SessionPlayerIdRequest;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.response.GameSessionResponseDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.response.QuestionDto;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.ws.AnswerStatsResponseDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.service.IGameSessionService;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.service.IWebSocketNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sessions")
@Slf4j
public class GameSessionController implements IGameSessionControllerSwagger {

    private final IGameSessionService gameSessionService;
    private final IWebSocketNotificationService webSocketNotificationService;

    @GetMapping
    public ResponseEntity<Page<GameSessionResponseDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(gameSessionService.getAll(pageable));
    }

    @PostMapping
    public ResponseEntity<GameSessionResponseDTO> create(@RequestBody GameSessionRequestDTO gameSession,
                                                         @RequestHeader("X-User-Id") String userId
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(gameSessionService.save(gameSession, userId));
    }

    @PostMapping("/{pin}/join")
    public ResponseEntity<GameSessionResponseDTO> joinGame(@PathVariable String pin,
                                                           @RequestBody JoinRequestDTO joinRequestDTO) {
        return ResponseEntity.ok(gameSessionService.joinGameSession(pin, joinRequestDTO));
    }

    @PostMapping("/{gameSessionId}/start")
    public ResponseEntity<String> startGame(@PathVariable String gameSessionId,
                                            @RequestHeader("X-User-Id") String userId) {
        log.info("UserId:{}", userId);
        QuestionDto question = gameSessionService.start(gameSessionId, userId);
        webSocketNotificationService.notifySessionStarted(gameSessionId, question);
        return ResponseEntity.ok("Game started successfully");
    }

    @GetMapping("/{gameSessionId}")
    public ResponseEntity<GameSessionResponseDTO> findById(@PathVariable String gameSessionId) {
        return ResponseEntity.ok(gameSessionService.findById(gameSessionId));
    }

    @GetMapping("/{gameSessionId}/questions/{questionId}/stats")
    public ResponseEntity<List<AnswerStatsResponseDTO>> getQuestionStats(@PathVariable String gameSessionId,
                                                                         @PathVariable String questionId){
        return ResponseEntity.ok(gameSessionService.getQuestionStats(gameSessionId, questionId));
    }

    @PostMapping("/{gameSessionId}/next")
    public ResponseEntity<Void> nextQuestion(@PathVariable String gameSessionId,
                                             @RequestHeader("X-User-Id") String userId)
    {
        QuestionDto question = gameSessionService.nextQuestion(gameSessionId, userId);
        webSocketNotificationService.notifyNextQuestion(gameSessionId, question);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{gameSessionId}")
    public ResponseEntity<String> deleteById(@PathVariable String gameSessionId) {
        gameSessionService.deleteById(gameSessionId);
        return ResponseEntity.ok("Game session with id: " + gameSessionId + " successfully deleted");
    }

    @PatchMapping("/{gameSessionId}/user/{username}")
    public ResponseEntity<String> updateUserSessionId(
            @PathVariable String gameSessionId,
            @PathVariable String username,
            @RequestBody SessionPlayerIdRequest sessionPlayerIdRequest) {
        gameSessionService.updateSessionPlayerId(gameSessionId, username, sessionPlayerIdRequest);
        return ResponseEntity.ok("User sessionId updated successfully");
    }


}
