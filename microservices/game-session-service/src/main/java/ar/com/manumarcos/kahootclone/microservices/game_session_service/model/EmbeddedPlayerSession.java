package ar.com.manumarcos.kahootclone.microservices.game_session_service.model;

import lombok.Data;

import java.util.List;

@Data
public class EmbeddedPlayerSession {

    private String userId;

    private int score;

    private List<EmbeddedPlayerAnswer> answers;
}
