package ar.com.manumarcos.kahootclone.microservices.game_session_service.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class EmbeddedPlayerAnswer {

    private String text;

    private String questionId;

    private EmbeddedOption selectedOption;
}
