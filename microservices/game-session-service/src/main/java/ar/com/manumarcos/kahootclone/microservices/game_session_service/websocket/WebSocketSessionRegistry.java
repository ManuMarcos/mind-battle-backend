package ar.com.manumarcos.kahootclone.microservices.game_session_service.websocket;

import ar.com.manumarcos.kahootclone.microservices.game_session_service.websocket.model.SessionInfo;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class WebSocketSessionRegistry {

    private final Map<String, SessionInfo> sessionMap = new HashMap<>();

    public void registerSession(String sessionId, SessionInfo sessionInfo){
        sessionMap.put(sessionId, sessionInfo);
    }

    public void removeSession(String sessionId){
        sessionMap.remove(sessionId);
    }

    public SessionInfo getSession(String sessionId){
        return sessionMap.get(sessionId);
    }

    public boolean containsSession(String sessionId){
        return sessionMap.containsKey(sessionId);
    }

    public Map<String, SessionInfo> getAllSessions(){
        return this.sessionMap;
    }


}
