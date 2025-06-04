package ar.com.manumarcos.kahootclone.microservices.quiz_service.dto;

import ar.com.manumarcos.kahootclone.microservices.quiz_service.model.Option;
import lombok.Data;

import java.util.List;

@Data
public class QuestionDTO {
    private String id;
    private String text;
    private int timeLimitSeconds;
    private List<OptionDTO> options;
}