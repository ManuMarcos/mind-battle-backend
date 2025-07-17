package ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.response;


import lombok.Data;

@Data
public class EmbeddedOptionResponseDTO {

    private String id;

    private String text;

    private int order;

    private boolean isCorrect;
}
