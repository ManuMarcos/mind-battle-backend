package ar.com.manumarcos.kahootclone.microservices.game_session_service.websocket.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayerJoinedDTO {
    private String username;
}
