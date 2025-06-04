package ar.com.manumarcos.kahootclone.microservices.game_session_service.model;

import ar.com.manumarcos.microservices.commons.dto.quiz.QuizResponseDTO;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "game_sessions")
@Builder
@Data
public class GameSession {

    @Id
    private String id;

    private String pin;

    private GameStatus status;

    private int currentQuestionIndex;

    private LocalDateTime questionStartTime;

    private EmbeddedQuiz quiz;

    @Builder.Default
    private List<EmbeddedPlayerSession> players =  new ArrayList<>();

}
