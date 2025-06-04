package ar.com.manumarcos.kahootclone.microservices.game_session_service.websocket.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseDTO {
    private String code;
    private String message;
}
