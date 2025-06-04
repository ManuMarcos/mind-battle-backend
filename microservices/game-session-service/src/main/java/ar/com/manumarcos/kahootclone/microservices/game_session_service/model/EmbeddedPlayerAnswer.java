package ar.com.manumarcos.kahootclone.microservices.game_session_service.model;

import lombok.Data;

import java.util.List;

@Data
public class EmbeddedPlayerAnswer {

    private String text;

    private List<EmbeddedOption> selectedOptions;
}
