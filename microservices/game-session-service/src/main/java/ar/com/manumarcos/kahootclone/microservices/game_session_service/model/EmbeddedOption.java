package ar.com.manumarcos.kahootclone.microservices.game_session_service.model;

import lombok.Data;

@Data
public class EmbeddedOption {
    private String id;

    private String text;

    private int order;

    private boolean isCorrect;
}
