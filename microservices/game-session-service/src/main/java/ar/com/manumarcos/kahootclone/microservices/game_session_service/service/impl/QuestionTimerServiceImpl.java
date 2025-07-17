package ar.com.manumarcos.kahootclone.microservices.game_session_service.service.impl;


import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.websocket.AnswerStatsResponseDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.service.IQuestionTimerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionTimerServiceImpl implements IQuestionTimerService {



    @Override
    public void startTimer(String gameSessionId, int timeLimitSeconds){
        AnswerStatsResponseDTO stats = null;

    }
}
