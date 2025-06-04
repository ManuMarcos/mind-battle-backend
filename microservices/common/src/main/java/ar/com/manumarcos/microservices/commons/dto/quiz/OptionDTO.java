package ar.com.manumarcos.microservices.commons.dto.quiz;

import lombok.Data;

@Data
public class OptionDTO {
    private String id;

    private String text;

    private boolean isCorrect;
}
