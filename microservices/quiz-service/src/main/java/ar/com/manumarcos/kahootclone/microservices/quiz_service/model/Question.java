package ar.com.manumarcos.kahootclone.microservices.quiz_service.model;


import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class Question {

    private String id;

    private String text;

    private int timeLimitSeconds;

    private List<Option> options;
}
