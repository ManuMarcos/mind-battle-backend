package ar.com.manumarcos.kahootclone.microservices.quiz_service.dto.request;

import lombok.Data;

@Data
public class OptionDTO {
    private String id;
    private String text;
    private boolean isCorrect;
}
