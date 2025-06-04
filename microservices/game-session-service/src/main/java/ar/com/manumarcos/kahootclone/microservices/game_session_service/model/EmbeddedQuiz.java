package ar.com.manumarcos.kahootclone.microservices.game_session_service.model;

import lombok.Data;

import java.util.List;

@Data
public class EmbeddedQuiz {

    private String id;

    private String title;

    private String description;

    private String createdBy;

    private List<EmbeddedQuestion> questions;
}
