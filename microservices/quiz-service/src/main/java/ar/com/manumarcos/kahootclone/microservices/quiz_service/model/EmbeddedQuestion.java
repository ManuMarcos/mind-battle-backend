package ar.com.manumarcos.kahootclone.microservices.quiz_service.model;


import lombok.Data;

import java.util.List;

@Data
public class EmbeddedQuestion {

    private String id;

    private String text;

    private int timeLimitSeconds;

    private List<EmbeddedOption> options;
}
