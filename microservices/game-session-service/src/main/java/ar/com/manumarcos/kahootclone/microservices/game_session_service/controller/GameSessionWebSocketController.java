package ar.com.manumarcos.kahootclone.microservices.game_session_service.controller;

import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.request.AnswerRequestDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.request.JoinRequestDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.service.IGameSessionService;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.ws.AnswerReceivedResponseDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.ws.EventMessageDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.ws.PlayerDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.model.EventType;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.service.IWebSocketNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class GameSessionWebSocketController {

    private final IGameSessionService gameSessionService;
    private final SimpMessagingTemplate messagingTemplate;
    private final IWebSocketNotificationService notificationService;

    @MessageMapping("/session/{gameSessionId}/join")
    public void handleJoinRequest(@Payload JoinRequestDTO joinRequestDTO, @DestinationVariable String gameSessionId,
                                  StompHeaderAccessor accessor) {
        log.info("Player '{}' trying to join game with pin '{}'", joinRequestDTO.getUsername(), gameSessionId);
        String sessionId = accessor.getSessionId();
        gameSessionService.registerWebSocketSession(gameSessionId, sessionId, joinRequestDTO.getUsername());
        PlayerDTO player = PlayerDTO
                .builder()
                .username(joinRequestDTO.getUsername())
                .build();
        notificationService.notifyPlayerJoined(gameSessionId, player);
    }

    @MessageMapping("/session/{gameSessionId}/answer")
    public void handleAnswer(@Payload AnswerRequestDTO answerRequestDTO, @DestinationVariable String gameSessionId) {
        log.info("New answer in gameSession with id '{}' by '{}'", gameSessionId, answerRequestDTO.getUsername());
        Long numberOfAnswers = gameSessionService.answer(gameSessionId, answerRequestDTO);
        AnswerReceivedResponseDTO answerReceived = AnswerReceivedResponseDTO
                .builder()
                .currentCount(numberOfAnswers)
                .build();
        notificationService.notifyAnswerReceived(gameSessionId, answerReceived);
    }

}
