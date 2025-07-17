package ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.ws;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnswerReceivedResponseDTO {
    private Long currentCount;
}
