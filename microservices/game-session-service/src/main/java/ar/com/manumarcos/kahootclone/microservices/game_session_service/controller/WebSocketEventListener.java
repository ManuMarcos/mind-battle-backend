package ar.com.manumarcos.kahootclone.microservices.game_session_service.controller;

import ar.com.manumarcos.kahootclone.microservices.game_session_service.service.IGameSessionService;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.ws.EventMessageDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.ws.PlayerDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.model.EventType;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.model.SessionInfo;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.repository.WebSocketSessionRegistry;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.service.IWebSocketNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.Optional;


@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final IGameSessionService gameSessionService;
    private final WebSocketSessionRegistry sessionRegistry;
    private final IWebSocketNotificationService notificationService;


    @EventListener
    public void handleWebSocketDisconnectEventListener(SessionDisconnectEvent event){
        String sessionId = event.getSessionId();
        log.info("Session with id : {} disconnected", sessionId);
        Optional<SessionInfo> sessionInfoOpt = sessionRegistry.getSession(sessionId);
        if(sessionInfoOpt.isPresent()) {
            SessionInfo sessionInfo = sessionInfoOpt.get();
            sessionRegistry.removeSession(sessionId);
            gameSessionService.removePlayerFromSession(sessionInfo);
            log.info("Session disconnect '{}'", sessionId);
            PlayerDTO player = PlayerDTO
                    .builder()
                    .username(sessionInfo.getUsername())
                    .build();
            notificationService.notifyPlayerLeft(sessionId,player );
        }
    }

    @EventListener
    public void handleSessionSubscribeEvent(SessionSubscribeEvent event){
        StompHeaderAccessor stompHeaderAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String destination = stompHeaderAccessor.getDestination();
        String sessionId = stompHeaderAccessor.getSessionId();
        log.info("New subscription: sessionId={}, destination={}", sessionId, destination);
    }


}
