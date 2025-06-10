package ar.com.manumarcos.kahootclone.microservices.game_session_service.model;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class EmbeddedPlayerSession {

    private String userId;

    private String username;

    private int score;

    private String sessionId;

    @Builder.Default
    private List<EmbeddedPlayerAnswer> answers = new ArrayList<>();
}
