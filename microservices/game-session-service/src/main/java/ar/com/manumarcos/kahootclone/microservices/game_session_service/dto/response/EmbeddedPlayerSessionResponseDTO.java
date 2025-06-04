package ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.response;

import ar.com.manumarcos.kahootclone.microservices.game_session_service.model.EmbeddedPlayerAnswer;
import lombok.Data;

import java.util.List;

@Data
public class EmbeddedPlayerSessionResponseDTO {
    private String userId;

    private int score;

    private List<EmbeddedPlayerAnswerResponseDTO> answers;
}
