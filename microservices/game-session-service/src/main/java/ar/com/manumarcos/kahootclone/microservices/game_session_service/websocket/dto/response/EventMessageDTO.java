package ar.com.manumarcos.kahootclone.microservices.game_session_service.websocket.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventMessageDTO <T>{
    private String event;
    private T data;
}
