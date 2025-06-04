package ar.com.manumarcos.kahootclone.microservices.game_session_service.websocket.controller;

import ar.com.manumarcos.kahootclone.microservices.game_session_service.service.IGameSessionService;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.websocket.dto.request.JoinRequestDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.websocket.dto.response.PlayerJoinedDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class GameController {

    private final IGameSessionService gameSessionService;

    @MessageMapping("/session/{pin}/join")
    @SendTo("/topic/session/{pin}")
    public PlayerJoinedDTO handleJoin(@Payload JoinRequestDTO joinRequestDTO, @DestinationVariable String pin){
        log.info("Player '{}' trying to join game with pin '{}'", joinRequestDTO.getUsername(), pin);
        gameSessionService.joinGameSession(pin, joinRequestDTO);
        return PlayerJoinedDTO.builder()
                .username(joinRequestDTO.getUsername())
                .build();
    }

}
