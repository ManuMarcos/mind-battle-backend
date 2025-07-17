package ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.websocket;


public record AnswerStatsResponseDTO(
        String optionId,
        String text,
        boolean correct,
        long count,
        int order
){}
