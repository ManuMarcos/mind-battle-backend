package ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.websocket;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayerDTO {
    private String username;
}
