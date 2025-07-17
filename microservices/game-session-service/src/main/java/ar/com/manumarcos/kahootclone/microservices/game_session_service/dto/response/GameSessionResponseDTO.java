package ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.response;

import ar.com.manumarcos.kahootclone.microservices.game_session_service.model.EmbeddedPlayerSession;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.model.GameStatus;
import ar.com.manumarcos.microservices.commons.dto.quiz.QuizResponseDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class GameSessionResponseDTO {

    private String id;

    private String pin;

    private GameStatus status;

    private String createdBy;

    private int currentQuestionIndex;

    private LocalDateTime questionStartTime;

    private EmbeddedQuizResponseDTO quiz;

    private List<EmbeddedPlayerSession> players;
}
