package ar.com.manumarcos.kahootclone.microservices.game_session_service.controller;

import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.request.GameSessionRequestDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.response.GameSessionResponseDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.service.IGameSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/game-sessions")
public class GameSessionController {

    private final IGameSessionService gameSessionService;

    @GetMapping
    public ResponseEntity<Page<GameSessionResponseDTO>> findAll(Pageable pageable){
        return ResponseEntity.ok(gameSessionService.getAll(pageable));
    }

    @PostMapping
    public ResponseEntity<GameSessionResponseDTO> create(@RequestBody GameSessionRequestDTO gameSession){
        return ResponseEntity.status(HttpStatus.CREATED).body( gameSessionService.save(gameSession));
    }

    @GetMapping("/{gameSessionId}")
    public ResponseEntity<GameSessionResponseDTO> findById(@PathVariable String gameSessionId){
        return ResponseEntity.ok(gameSessionService.findById(gameSessionId));
    }

    @DeleteMapping("/{gameSessionId}")
    public ResponseEntity<String> deleteById(@PathVariable String gameSessionId){
        gameSessionService.deleteById(gameSessionId);
        return ResponseEntity.ok("Game session with id: " + gameSessionId + " successfully deleted");
    }

}
