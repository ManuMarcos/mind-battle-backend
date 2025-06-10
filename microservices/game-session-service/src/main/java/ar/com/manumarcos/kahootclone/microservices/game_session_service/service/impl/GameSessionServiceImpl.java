package ar.com.manumarcos.kahootclone.microservices.game_session_service.service.impl;

import ar.com.manumarcos.kahootclone.microservices.game_session_service.client.IQuizClient;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.request.GameSessionRequestDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.request.JoinRequestDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.request.SessionPlayerIdRequest;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.response.EmbeddedOptionResponseDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.response.EmbeddedQuestionResponseDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.response.GameSessionResponseDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.response.QuestionDto;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.exception.*;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.mapper.gamesession.IGameSessionMapper;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.mapper.quiz.IQuestionMapper;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.mapper.quiz.IQuizMapper;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.model.EmbeddedPlayerSession;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.model.EmbeddedQuiz;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.model.GameSession;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.model.GameStatus;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.repository.IGameSessionRepository;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.service.IGameSessionService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class GameSessionServiceImpl implements IGameSessionService {

    private final IGameSessionRepository gameSessionRepository;
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
    public GameSessionResponseDTO save(GameSessionRequestDTO gameSessionRequestDTO) {
        EmbeddedQuiz quiz = this.getQuizById(gameSessionRequestDTO.getQuizId());
        String pin = this.generatePin();
        while (gameSessionRepository.existsByPinAndStatusNotIn(pin,
                List.of(GameStatus.CREATED, GameStatus.IN_PROGRESS))) {
            pin = this.generatePin();
        }
        GameSession gameSession = GameSession.builder()
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
        Optional<GameSession> gameSessionOpt = gameSessionRepository.findGameSessionByPinAndStatus(pin, GameStatus.CREATED);
        if (gameSessionOpt.isPresent()) {
            GameSession gameSession = gameSessionOpt.get();
            EmbeddedPlayerSession playerSession = EmbeddedPlayerSession.builder()
                    .username(joinRequestDTO.getUsername())
                    .score(0)
                    .build();
            gameSession.getPlayers().add(playerSession);
            gameSessionRepository.save(gameSession);
            return gameSessionMapper.toDTO(gameSession);
        } else {
            throw new GameSessionPinNotFound(pin);
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
        player.get().setSessionId(sessionPlayerIdRequest.getSessionId());
        gameSessionRepository.save(gameSession);
    }

    @Override
    public void removePlayerFromSession(String gameSessionId, String username) {
        GameSession gameSession = this.getGameSessionById(gameSessionId);
        boolean removed = gameSession.getPlayers().removeIf(p -> p.getUsername().equals(username));
        if (!removed) {
            throw new UserNotFoundInSessionException(username, gameSessionId);
        }
        gameSessionRepository.save(gameSession);
    }

    @Override
    public boolean existsUserInGame(String gameSessionId, String username) {
        GameSession gameSession = this.getGameSessionById(gameSessionId);
        return gameSession.getPlayers().stream().anyMatch((p) -> p.getUsername().equals(username));
    }

    @Override
    public QuestionDto start(String gameSessionId) {
        GameSession gameSession = this.getGameSessionById(gameSessionId);
        if (!gameSession.getStatus().equals(GameStatus.CREATED)) {
            throw new GameSessionInvalid(gameSessionId);
        }
        QuestionDto question = questionMapper.toQuestionDTO(gameSession.getQuiz().
                getQuestions().get(gameSession.getCurrentQuestionIndex()));
        question.setQuestionStartTime(Instant.now());
        gameSession.setStatus(GameStatus.IN_PROGRESS);
        gameSession.setCurrentQuestionIndex(gameSession.getCurrentQuestionIndex() + 1);
        gameSessionRepository.save(gameSession);
        return question;
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
