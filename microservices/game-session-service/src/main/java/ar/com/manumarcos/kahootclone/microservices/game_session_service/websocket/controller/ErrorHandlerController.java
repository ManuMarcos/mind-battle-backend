package ar.com.manumarcos.kahootclone.microservices.game_session_service.websocket.controller;

import ar.com.manumarcos.kahootclone.microservices.game_session_service.exception.GameSessionNotFound;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.exception.GameSessionPinNotFound;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.websocket.dto.response.ErrorResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;


@ControllerAdvice
@Slf4j
public class ErrorHandlerController {

    @MessageExceptionHandler(GameSessionPinNotFound.class)
    @SendToUser("/queue/errors")
    public ErrorResponseDTO handleGameSessionNotFound(Exception ex){
        log.info("Session not found: '{}'", ex.getMessage());
        return ErrorResponseDTO.builder()
                .code("SESSION_NOT_FOUND")
                .message(ex.getMessage())
                .build();
    }
}
