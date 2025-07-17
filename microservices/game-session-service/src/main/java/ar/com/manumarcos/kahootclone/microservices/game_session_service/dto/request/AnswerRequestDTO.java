package ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnswerRequestDTO {
    private String username;
    private String questionId;
    private String selectedOptionId;
}
