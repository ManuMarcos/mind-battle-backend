package ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.response;

import ar.com.manumarcos.kahootclone.microservices.game_session_service.model.EmbeddedOption;
import lombok.Data;

import java.util.List;

@Data
public class EmbeddedPlayerAnswerResponseDTO {

    private String text;

    private EmbeddedOptionResponseDTO selectedOption;
}
