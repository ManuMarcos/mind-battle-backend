    package ar.com.manumarcos.kahootclone.microservices.game_session_service.service.impl;

    import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.response.QuestionDto;
    import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.ws.AnswerReceivedResponseDTO;
    import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.ws.EventMessageDTO;
    import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.ws.PlayerDTO;
    import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.ws.AnswerStatsResponseDTO;
    import ar.com.manumarcos.kahootclone.microservices.game_session_service.model.EventType;
    import ar.com.manumarcos.kahootclone.microservices.game_session_service.service.IGameSessionService;
    import ar.com.manumarcos.kahootclone.microservices.game_session_service.service.IWebSocketNotificationService;
    import lombok.RequiredArgsConstructor;
    import org.springframework.messaging.simp.SimpMessagingTemplate;
    import org.springframework.stereotype.Service;

    import java.util.List;
    import java.util.concurrent.Executors;
    import java.util.concurrent.ScheduledExecutorService;
    import java.util.concurrent.TimeUnit;

    @Service
    @RequiredArgsConstructor
    public class WebSocketNotificationServiceImpl implements IWebSocketNotificationService {

        private final SimpMessagingTemplate messagingTemplate;
        private final IGameSessionService gameSessionService;
        private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


        @Override
        public void notifySessionStarted(String gameSessionId, QuestionDto question) {
            //questionTimerServiceImpl.startTimer(gameSessionId, question.getTimeLimitSeconds());
            scheduler.schedule(() -> {
                this.notifyQuestionEnd(gameSessionId, question.getId());
            },question.getTimeLimitSeconds(), TimeUnit.SECONDS );
            EventMessageDTO<QuestionDto> eventResponse = new EventMessageDTO<>(
                    EventType.GAME_STARTED,
                    question
            );
            messagingTemplate.convertAndSend("/topic/session." + gameSessionId, eventResponse);
        }

        @Override
        public void notifyNextQuestion(String gameSessionId, QuestionDto question) {
            EventMessageDTO<QuestionDto> eventResponse = new EventMessageDTO<>(
                    EventType.NEXT_QUESTION,
                    question
            );
            messagingTemplate.convertAndSend("/topic/session." + gameSessionId, eventResponse);
        }

        @Override
        public void notifyPlayerJoined(String gameSessionId, PlayerDTO playerDTO) {
            EventMessageDTO<PlayerDTO> eventResponse = new EventMessageDTO<>(
                    EventType.PLAYER_JOINED,
                    playerDTO
            );
            messagingTemplate.convertAndSend("/topic/session." + gameSessionId, eventResponse);
        }

        @Override
        public void notifyAnswerReceived(String gameSessionId, AnswerReceivedResponseDTO answerReceivedResponseDTO) {
            EventMessageDTO<AnswerReceivedResponseDTO> eventResponse = new EventMessageDTO<>(
                    EventType.ANSWER_RECEIVED,
                    answerReceivedResponseDTO
            );
            messagingTemplate.convertAndSend("/topic/session." + gameSessionId, eventResponse);
        }

        @Override
        public void notifyQuestionEnd(String gameSessionId, String questionId) {
            EventMessageDTO<List<AnswerStatsResponseDTO>> eventResponse = new EventMessageDTO<>(
                    EventType.QUESTION_END,
                    gameSessionService.getQuestionStats(gameSessionId, questionId)
            );
            messagingTemplate.convertAndSend("/topic/session." + gameSessionId, eventResponse);
        }

        @Override
        public void notifyPlayerLeft(String gameSessionId, PlayerDTO playerDTO) {
            EventMessageDTO<PlayerDTO> eventResponse = new EventMessageDTO<>(
                    EventType.PLAYER_LEFT,
                    playerDTO
            );
            messagingTemplate.convertAndSend("/topic/session." + gameSessionId, eventResponse);
        }


    }
