package ar.com.manumarcos.kahootclone.microservices.game_session_service.websocket.controller;

import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.request.JoinRequestDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.response.GameSessionResponseDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.response.QuestionDto;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.exception.UserNotFoundInSessionException;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.model.GameSession;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.service.IGameSessionService;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.websocket.WebSocketSessionRegistry;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.websocket.dto.response.EventMessageDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.websocket.dto.response.PlayerDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.websocket.model.SessionInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:5173")
public class GameController {

    private final IGameSessionService gameSessionService;
    private final SimpMessagingTemplate messagingTemplate;
    private final WebSocketSessionRegistry sessionRegistry;

    @MessageMapping("/session/{gameSessionId}/join")
    public void handleJoinRequest(@Payload JoinRequestDTO joinRequestDTO, @DestinationVariable String gameSessionId,
                                  Message<?> message) {
        log.info("Player '{}' trying to join game with pin '{}'", joinRequestDTO.getUsername(), gameSessionId);
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String sessionId = accessor.getSessionId();
        if(!gameSessionService.existsUserInGame(gameSessionId, joinRequestDTO.getUsername())){
            throw new UserNotFoundInSessionException(joinRequestDTO.getUsername(), gameSessionId);
        }
        sessionRegistry.registerSession(sessionId, SessionInfo.builder()
                .gameSessionId(gameSessionId)
                .username(joinRequestDTO.getUsername())
                .role("PLAYER")
                .build());
        EventMessageDTO<PlayerDTO> eventResponse = new EventMessageDTO<>(
                "PLAYER_JOINED",
                PlayerDTO.builder().username(joinRequestDTO.getUsername())
                        .build()
        );
        messagingTemplate.convertAndSend("/topic/session/" + gameSessionId, eventResponse);
    }

    @MessageMapping("/session/{gameSessionId}/start")
    public void handleStartGame(@DestinationVariable String gameSessionId){
        QuestionDto questionDto = gameSessionService.start(gameSessionId);
        EventMessageDTO<QuestionDto> eventResponse = new EventMessageDTO<>(
                "GAME_STARTED",
                questionDto
        );
        messagingTemplate.convertAndSend("/topic/session/" + gameSessionId, eventResponse);
    }




}
