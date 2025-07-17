package ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class QuestionDto {
    private String id;

    private String text;

    private int timeLimitSeconds;

    private List<EmbeddedOptionResponseDTO> options;

    private Instant questionStartTime;

    private boolean hasNext;
}
