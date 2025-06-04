package ar.com.manumarcos.microservices.commons.dto.quiz;


import lombok.Data;

import java.util.List;

@Data
public class QuizResponseDTO {

    private String id;

    private String title;

    private String description;

    private String createdBy;

    private List<QuestionDTO> questions;
}
