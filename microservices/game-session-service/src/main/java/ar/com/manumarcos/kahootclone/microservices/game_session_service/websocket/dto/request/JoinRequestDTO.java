package ar.com.manumarcos.kahootclone.microservices.game_session_service.websocket.dto.request;

import lombok.Data;

@Data
public class JoinRequestDTO {
    private String username;
    private String userId;
}
