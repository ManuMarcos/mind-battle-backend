package ar.com.manumarcos.kahootclone.microservices.quiz_service.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class QuizRequestDTO {
    private String id;

    private String title;

    private String description;

    private String createdBy;

    private List<QuestionDTO> questions;

}