package ar.com.manumarcos.kahootclone.microservices.game_session_service.repository;

import ar.com.manumarcos.kahootclone.microservices.game_session_service.model.SessionInfo;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class WebSocketSessionRegistry {

    private final Map<String, SessionInfo> sessionMap = new HashMap<>();

    public void registerSession(String sessionId, SessionInfo sessionInfo){
        sessionMap.put(sessionId, sessionInfo);
    }

    public void removeSession(String sessionId){
        sessionMap.remove(sessionId);
    }

    public Optional<SessionInfo> getSession(String sessionId){
        return Optional.ofNullable(sessionMap.get(sessionId));
    }

    public boolean containsSession(String sessionId){
        return sessionMap.containsKey(sessionId);
    }

    public Map<String, SessionInfo> getAllSessions(){
        return this.sessionMap;
    }


}
