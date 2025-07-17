package ar.com.manumarcos.kahootclone.microservices.game_session_service.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SessionInfo {
    private String username;
    private String gameSessionId;
    private String role;
}
