package ar.com.manumarcos.kahootclone.microservices.game_session_service.websocket;

import ar.com.manumarcos.kahootclone.microservices.game_session_service.service.IGameSessionService;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.websocket.dto.response.EventMessageDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.websocket.dto.response.PlayerDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.websocket.model.SessionInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;


@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final IGameSessionService gameSessionService;
    private final WebSocketSessionRegistry sessionRegistry;
    private final SimpMessagingTemplate messagingTemplate;


    @EventListener
    public void handleWebSocketDisconnectEventListener(SessionDisconnectEvent event){
        String sessionId = StompHeaderAccessor.wrap((event.getMessage())).getSessionId();
        log.info("Session with id : {} disconnected", sessionId);
        SessionInfo sessionInfo = sessionRegistry.getSession(sessionId);
        sessionRegistry.removeSession(sessionId);
        if(sessionInfo != null){
            log.info("Removing player from session");
            gameSessionService.removePlayerFromSession(sessionInfo.getGameSessionId(),
                    sessionInfo.getUsername() );
        }
        log.info("Session disconnect '{}'", sessionId);

        EventMessageDTO<PlayerDTO> eventResponse = new EventMessageDTO<>(
                "PLAYER_LEFT",
                PlayerDTO.builder()
                        .username(sessionInfo.getUsername()).build()
        );
        messagingTemplate.convertAndSend("/topic/session/" + sessionInfo.getGameSessionId(),
                eventResponse);
    }


}
