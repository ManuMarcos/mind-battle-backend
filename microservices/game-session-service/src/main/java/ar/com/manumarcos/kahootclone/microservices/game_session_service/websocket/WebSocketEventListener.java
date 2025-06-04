package ar.com.manumarcos.kahootclone.microservices.game_session_service.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;


@Slf4j
@Component
public class WebSocketEventListener {

    @EventListener
    public void handleWebSocketDisconnectEventListener(SessionDisconnectEvent event){
        String sessionId = event.getSessionId();
        log.info("Session disconnect '{}'", sessionId);
    }
}
