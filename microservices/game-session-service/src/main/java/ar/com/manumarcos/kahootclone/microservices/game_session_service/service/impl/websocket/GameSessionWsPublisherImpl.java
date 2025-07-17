    package ar.com.manumarcos.kahootclone.microservices.game_session_service.service.impl.websocket;

    import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.response.QuestionDto;
    import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.websocket.AnswerReceivedResponseDTO;
    import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.websocket.EventMessageDTO;
    import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.websocket.PlayerDTO;
    import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.websocket.AnswerStatsResponseDTO;
    import ar.com.manumarcos.kahootclone.microservices.game_session_service.model.EventType;
    import ar.com.manumarcos.kahootclone.microservices.game_session_service.service.IGameSessionService;
    import ar.com.manumarcos.kahootclone.microservices.game_session_service.service.websocket.IGameSessionWsPublisher;
    import lombok.RequiredArgsConstructor;
    import org.springframework.messaging.simp.SimpMessagingTemplate;
    import org.springframework.stereotype.Service;

    import java.util.List;
    import java.util.concurrent.Executors;
    import java.util.concurrent.ScheduledExecutorService;
    import java.util.concurrent.TimeUnit;

    @Service
    @RequiredArgsConstructor
    public class GameSessionWsPublisherImpl implements IGameSessionWsPublisher {

        private final SimpMessagingTemplate messagingTemplate;
        private final IGameSessionService gameSessionService;
        private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


        @Override
        public void publishSessionStarted(String gameSessionId, QuestionDto question) {
            //questionTimerServiceImpl.startTimer(gameSessionId, question.getTimeLimitSeconds());
            scheduler.schedule(() -> {
                this.publishQuestionEnd(gameSessionId, question.getId());
            },question.getTimeLimitSeconds(), TimeUnit.SECONDS );
            EventMessageDTO<QuestionDto> eventResponse = new EventMessageDTO<>(
                    EventType.GAME_STARTED,
                    question
            );
            messagingTemplate.convertAndSend("/topic/session." + gameSessionId, eventResponse);
        }

        @Override
        public void publishNextQuestion(String gameSessionId, QuestionDto question) {
            EventMessageDTO<QuestionDto> eventResponse = new EventMessageDTO<>(
                    EventType.NEXT_QUESTION,
                    question
            );
            messagingTemplate.convertAndSend("/topic/session." + gameSessionId, eventResponse);
        }

        @Override
        public void publishPlayerJoined(String gameSessionId, PlayerDTO playerDTO) {
            EventMessageDTO<PlayerDTO> eventResponse = new EventMessageDTO<>(
                    EventType.PLAYER_JOINED,
                    playerDTO
            );
            messagingTemplate.convertAndSend("/topic/session." + gameSessionId, eventResponse);
        }

        @Override
        public void publishAnswerReceived(String gameSessionId, AnswerReceivedResponseDTO answerReceivedResponseDTO) {
            EventMessageDTO<AnswerReceivedResponseDTO> eventResponse = new EventMessageDTO<>(
                    EventType.ANSWER_RECEIVED,
                    answerReceivedResponseDTO
            );
            messagingTemplate.convertAndSend("/topic/session." + gameSessionId, eventResponse);
        }

        @Override
        public void publishQuestionEnd(String gameSessionId, String questionId) {
            EventMessageDTO<List<AnswerStatsResponseDTO>> eventResponse = new EventMessageDTO<>(
                    EventType.QUESTION_END,
                    gameSessionService.getQuestionStats(gameSessionId, questionId)
            );
            messagingTemplate.convertAndSend("/topic/session." + gameSessionId, eventResponse);
        }

        @Override
        public void publishPlayerLeft(String gameSessionId, PlayerDTO playerDTO) {
            EventMessageDTO<PlayerDTO> eventResponse = new EventMessageDTO<>(
                    EventType.PLAYER_LEFT,
                    playerDTO
            );
            messagingTemplate.convertAndSend("/topic/session." + gameSessionId, eventResponse);
        }


    }
