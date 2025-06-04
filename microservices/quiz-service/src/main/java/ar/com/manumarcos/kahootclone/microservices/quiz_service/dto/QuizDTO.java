package ar.com.manumarcos.kahootclone.microservices.quiz_service.dto;

import ar.com.manumarcos.kahootclone.microservices.quiz_service.model.Question;
import lombok.Data;

import java.util.List;

@Data
public class QuizDTO {
    private String id;

    private String title;

    private String description;

    private String createdBy;

    private List<QuestionDTO> questions;

}