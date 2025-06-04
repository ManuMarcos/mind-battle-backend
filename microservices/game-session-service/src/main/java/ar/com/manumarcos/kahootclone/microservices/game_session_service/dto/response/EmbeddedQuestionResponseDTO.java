package ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class EmbeddedQuestionResponseDTO {
    private String id;

    private String text;

    private int timeLimitSeconds;

    private List<EmbeddedOptionResponseDTO> options;
}
