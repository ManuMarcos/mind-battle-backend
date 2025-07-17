package ar.com.manumarcos.kahootclone.microservices.game_session_service.controller.swagger;

import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.request.GameSessionRequestDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.request.JoinRequestDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.request.SessionPlayerIdRequest;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.response.GameSessionResponseDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.ws.AnswerStatsResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@Tag(name = "Game Sessions", description = "Endpoints para gestionar las sesiones de juego")
public interface IGameSessionControllerSwagger {

    @Operation(
            summary = "Obtener todas las sesiones de juego",
            description = "Endpoint para obtener todas las sesiones de juego paginadas"
    )
    ResponseEntity<Page<GameSessionResponseDTO>> findAll(Pageable pageable);

    @Operation(
            summary = "Crear una sesion de juego",
            description = "Endpoint para crear una nueva sesion de juego"
    )
    ResponseEntity<GameSessionResponseDTO> create(@RequestBody GameSessionRequestDTO gameSession,
                                                  @RequestHeader("X-User-Id") String userId
    );

    @Operation(
            summary = "Unirse a una sesion de juego",
            description = "Endpoint para unirse a una sesion de juego que este creada en estado CREATE (creada)"
    )
    ResponseEntity<GameSessionResponseDTO> joinGame(@PathVariable String pin,
                                                    @RequestBody JoinRequestDTO joinRequestDTO);


    @Operation(
            summary = "Iniciar partida",
            description = "Endpoint para iniciar una partida siempre y cuando la sesion se encuentre en estado CREATE (creada)"
    )
    ResponseEntity<String> startGame(@PathVariable String gameSessionId,
                                     @RequestHeader("X-User-Id") String userId);

    @Operation(
            summary = "Buscar sesion por id",
            description = "Endpoint para buscar una sesion por su identificador (id)"
    )
    ResponseEntity<GameSessionResponseDTO> findById(@PathVariable String gameSessionId);

    @Operation(
            summary = "Obtener las estadisticas de una pregunta de una sesion",
            description = "Endpoint para obtener las estadisticas de una pregunta en especifico de una partida"
    )
    ResponseEntity<List<AnswerStatsResponseDTO>> getQuestionStats(@PathVariable String gameSessionId,
                                                                  @PathVariable String questionId);

    @Operation(
            summary = "Avanzar pregunta",
            description = "Endpoint para avanzar a la siguiente pregunta. Siempre y cuando hayan mas preguntas"
    )
    ResponseEntity<Void> nextQuestion(@PathVariable String gameSessionId,
                                      @RequestHeader("X-User-Id") String userId);

    @Operation(
            summary = "Eliminar sesion por id",
            description = "Endpoint para eliminar una sesion por su identificador (id)"
    )
    ResponseEntity<String> deleteById(@PathVariable String gameSessionId);

    ResponseEntity<String> updateUserSessionId(
            @PathVariable String gameSessionId,
            @PathVariable String username,
            @RequestBody SessionPlayerIdRequest sessionPlayerIdRequest);

}
