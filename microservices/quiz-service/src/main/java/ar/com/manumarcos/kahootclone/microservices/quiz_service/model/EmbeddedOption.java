package ar.com.manumarcos.kahootclone.microservices.quiz_service.model;

import lombok.Data;

@Data
public class EmbeddedOption {

    private String id;

    private String text;

    private boolean isCorrect;
}
