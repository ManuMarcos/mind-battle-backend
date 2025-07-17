package ar.com.manumarcos.kahootclone.microservices.game_session_service.service.impl;

import ar.com.manumarcos.kahootclone.microservices.game_session_service.client.IQuizClient;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.request.AnswerRequestDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.request.GameSessionRequestDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.request.JoinRequestDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.request.SessionPlayerIdRequest;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.response.GameSessionResponseDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.response.QuestionDto;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.exception.*;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.mapper.gamesession.IGameSessionMapper;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.mapper.quiz.IQuestionMapper;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.mapper.quiz.IQuizMapper;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.model.*;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.repository.GameSessionRepository;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.service.IGameSessionService;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.ws.AnswerStatsResponseDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.repository.WebSocketSessionRegistry;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameSessionServiceImpl implements IGameSessionService {

    private final GameSessionRepository gameSessionRepository;
    private final WebSocketSessionRegistry sessionRegistry;
    private final IGameSessionMapper gameSessionMapper;
    private final IQuestionMapper questionMapper;
    private final IQuizMapper quizMapper;
    private final IQuizClient quizClient;


    @Override
    public Page<GameSessionResponseDTO> getAll(Pageable pageable) {
        Page<GameSession> gameSessions = gameSessionRepository.findAll(pageable);
        return gameSessions.map(gameSessionMapper::toDTO);
    }

    @Override
    public GameSessionResponseDTO save(GameSessionRequestDTO gameSessionRequestDTO, String userId) {
        EmbeddedQuiz quiz = this.getQuizById(gameSessionRequestDTO.getQuizId());
        String pin = this.generatePin();
        while (gameSessionRepository.existsByPinAndStatusNotIn(pin,
                List.of(GameStatus.CREATED, GameStatus.IN_PROGRESS))) {
            pin = this.generatePin();
        }
        GameSession gameSession = GameSession.builder()
                .createdBy(userId)
                .pin(pin)
                .status(GameStatus.CREATED)
                .currentQuestionIndex(0)
                .quiz(quiz)
                .build();
        return gameSessionMapper.toDTO(gameSessionRepository.save(gameSession));
    }

    @Override
    public GameSessionResponseDTO findById(String gameSessionId) {
        GameSession gameSession = gameSessionRepository.findById(gameSessionId).orElseThrow(
                () -> new GameSessionNotFound(gameSessionId)
        );
        return gameSessionMapper.toDTO(gameSession);
    }

    @Override
    public void deleteById(String gameSessionId) {
        if (gameSessionRepository.findById(gameSessionId).isPresent()) {
            gameSessionRepository.deleteById(gameSessionId);
        } else {
            throw new GameSessionNotFound(gameSessionId);
        }
    }

    @Override
    public GameSessionResponseDTO joinGameSession(String pin, JoinRequestDTO joinRequestDTO) {
        GameSession gameSession = gameSessionRepository.findGameSessionByPinAndStatus(pin, GameStatus.CREATED)
                .orElseThrow(() -> new GameSessionNotFound(pin));
        if (gameSession.getPlayers().stream().filter((p) -> p.getUsername().equals(joinRequestDTO.getUsername()))
                .findFirst().isEmpty()) {
            EmbeddedPlayerSession playerSession = EmbeddedPlayerSession.builder()
                    .username(joinRequestDTO.getUsername())
                    .score(0)
                    .build();
            gameSession.getPlayers().add(playerSession);
            gameSessionRepository.save(gameSession);
        }
        return gameSessionMapper.toDTO(gameSession);
    }

    @Override
    public void registerWebSocketSession(String gameSessionId, String sessionId, String username) {
        GameSession gameSession = this.getGameSessionById(gameSessionId);
        if(!gameSession.getStatus().equals(GameStatus.CREATED)){
            throw new InvalidGameSession(gameSessionId, GameStatus.CREATED.toString());
        }
        System.out.println("Register user: " + username + " in WebSocket");
        if (this.existsUserInGame(gameSessionId, username)){
            sessionRegistry.registerSession(sessionId, SessionInfo.builder()
                    .gameSessionId(gameSessionId)
                    .username(username)
                    .role("PLAYER")
                    .build());
        }
        else{
            throw new UserNotFoundInSessionException(username, gameSessionId);
        }
    }

    @Override
    public void updateSessionPlayerId(String gameSessionId, String username, SessionPlayerIdRequest sessionPlayerIdRequest) {
        Optional<GameSession> gameSessionOpt = gameSessionRepository.findById(gameSessionId);
        if (gameSessionOpt.isEmpty()) {
            throw new GameSessionNotFound(gameSessionId);
        }
        GameSession gameSession = gameSessionOpt.get();
        Optional<EmbeddedPlayerSession> player = gameSession.getPlayers()
                .stream().filter((p) -> p.getUsername().equals(username)).findFirst();
        if (player.isEmpty()) {
            throw new UserNotFoundInSessionException(username, gameSessionId);
        }
        gameSessionRepository.save(gameSession);
    }

    @Override
    public void removePlayerFromSession(SessionInfo sessionInfo) {
        GameSession gameSession = this.getGameSessionById(sessionInfo.getGameSessionId());
        if(gameSession.getStatus().equals(GameStatus.CREATED)){
            boolean removed = gameSession.getPlayers().removeIf(p -> p.getUsername().equals(sessionInfo.getUsername()));
            if (!removed) {
                throw new UserNotFoundInSessionException(sessionInfo.getUsername(), sessionInfo.getGameSessionId());
            }
            gameSessionRepository.save(gameSession);
        }
    }

    @Override
    public boolean existsUserInGame(String gameSessionId, String username) {
        GameSession gameSession = this.getGameSessionById(gameSessionId);
        return gameSession.getPlayers().stream().anyMatch((p) -> p.getUsername().equals(username));
    }

    @Override
    public QuestionDto start(String gameSessionId, String userId) {
        GameSession gameSession = this.getGameSessionById(gameSessionId);
        if (!gameSession.getStatus().equals(GameStatus.CREATED)) {
            throw new InvalidGameSession(gameSessionId, GameStatus.CREATED.toString());
        }
        if(!gameSession.getCreatedBy().equals(userId)){
            throw new NotGameHostException(userId, gameSessionId);
        }
        QuestionDto question = questionMapper.toQuestionDTO(gameSession.getQuiz().
                getQuestions().get(gameSession.getCurrentQuestionIndex()));
        question.setQuestionStartTime(Instant.now());
        gameSession.setStatus(GameStatus.IN_PROGRESS);
        gameSession.setCurrentQuestionIndex(gameSession.getCurrentQuestionIndex() + 1);
        gameSessionRepository.save(gameSession);
        return question;
    }

    @Override
    public QuestionDto nextQuestion(String gameSessionId, String userId) {
        GameSession gameSession = this.getGameSessionById(gameSessionId);
        int currentQuestion = gameSession.getCurrentQuestionIndex();
        if(!gameSession.getStatus().equals(GameStatus.IN_PROGRESS)){
            throw new InvalidGameSession(gameSessionId, GameStatus.IN_PROGRESS.toString());
        }
        int questionsLength = gameSession.getQuiz().getQuestions().size();
        if(currentQuestion > (questionsLength - 1)){
            throw new GameAlreadyFinishedException(gameSessionId);
        }
        if(!gameSession.getCreatedBy().equals(userId)){
            throw new NotGameHostException(userId, gameSessionId);
        }
        //TODO: Validar que no se haya querido pasar la pregunta cuando se esta contestando?
        QuestionDto question = questionMapper.toQuestionDTO(gameSession.getQuiz().getQuestions()
                .get(currentQuestion));
        question.setQuestionStartTime(Instant.now());
        gameSession.setCurrentQuestionIndex(currentQuestion + 1);
        question.setHasNext(currentQuestion + 1 != gameSession.getQuiz().getQuestions().size() - 1);
        gameSessionRepository.save(gameSession);
        return question;
    }


    @Override
    public Long answer(String gameSessionId, AnswerRequestDTO answerRequestDTO) {
        GameSession gameSession = this.getGameSessionById(gameSessionId);
        String playerUsername = answerRequestDTO.getUsername();
        String questionId = answerRequestDTO.getQuestionId();
        String optionId = answerRequestDTO.getSelectedOptionId();
        EmbeddedPlayerSession player = gameSession.getPlayers().stream()
                .filter((p) -> p.getUsername().equals(playerUsername)).findFirst()
                .orElseThrow(() -> new UserNotFoundInSessionException(playerUsername, gameSessionId));
        EmbeddedQuestion question = gameSession.getQuiz().getQuestions().stream()
                .filter((q) -> q.getId().equals(questionId)).findFirst()
                .orElseThrow(() -> new InvalidQuestionException(answerRequestDTO.getQuestionId(), gameSessionId));
        EmbeddedOption option = question.getOptions().stream().filter((opt) -> opt.getId().equals(optionId))
                .findFirst().orElseThrow(() -> new InvalidOptionException(optionId, questionId, gameSessionId));
        if (option.isCorrect()) {
            player.setScore(player.getScore() + 5);
        }
        player.getAnswers().add(EmbeddedPlayerAnswer.builder()

                .questionId(questionId)
                .text(question.getText())
                .selectedOption(option)
                .build());
        gameSessionRepository.save(gameSession);
        return gameSession.getPlayers().stream().filter((p) -> p.getAnswers()
                .stream().anyMatch((anw) -> anw.getQuestionId().equals(optionId))).count();
    }

    @Override
    public List<AnswerStatsResponseDTO> getQuestionStats(String gameSessionId, String questionId) {
        GameSession gameSession = this.getGameSessionById(gameSessionId);

        EmbeddedQuestion question = gameSession.getQuiz().getQuestions().stream()
                .filter(q -> q.getId().equals(questionId))
                .findFirst()
                .orElseThrow(() -> new InvalidQuestionException(questionId, gameSessionId));
        List<AnswerStatsResponseDTO> stats = gameSessionRepository.getStatsForQuestion(gameSessionId,questionId);
        Map<String, Long> options = stats.stream().collect(
                Collectors.toMap(AnswerStatsResponseDTO::optionId, AnswerStatsResponseDTO::count)
        );

        List<AnswerStatsResponseDTO> result = question.getOptions().stream()
                .map(option -> new AnswerStatsResponseDTO(
                        option.getId(),
                        option.getText(),
                        option.isCorrect(),
                        options.getOrDefault(option.getId(), 0L), // usa 0 si no hay respuestas
                        option.getOrder()
                ))
                .sorted(Comparator.comparingInt(AnswerStatsResponseDTO::order)) // asegura el orden
                .toList();
        return result;
    }

    private EmbeddedQuiz getQuizById(String id) {
        try {
            return quizMapper.toEntity(quizClient.getQuizById(id));
        } catch (FeignException.NotFound e) {
            throw new QuizNotFoundException(id);
        }
    }

    private String generatePin() {
        int min = 100_000;
        int max = 999_999;
        return String.valueOf(ThreadLocalRandom.current().nextInt(min, max + 1));
    }

    private GameSession getGameSessionById(String gameSessionId) {
        return gameSessionRepository.findById(gameSessionId).orElseThrow(() ->
                new GameSessionNotFound(gameSessionId)
        );
    }
}
