package ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class EmbeddedQuizResponseDTO {
    private String id;

    private String title;

    private String description;

    private String createdBy;

    private List<EmbeddedQuestionResponseDTO> questions;
}
