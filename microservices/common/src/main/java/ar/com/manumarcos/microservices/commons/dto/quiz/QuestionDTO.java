package ar.com.manumarcos.microservices.commons.dto.quiz;

import lombok.Data;

import java.util.List;


@Data
public class QuestionDTO {

    private String id;

    private String text;

    private int timeLimitSeconds;

    private List<OptionDTO> options;
}
