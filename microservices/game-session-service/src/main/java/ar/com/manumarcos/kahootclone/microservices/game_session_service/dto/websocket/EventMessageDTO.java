package ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.websocket;

import ar.com.manumarcos.kahootclone.microservices.game_session_service.model.EventType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventMessageDTO <T>{
    private EventType event;
    private T data;
}
