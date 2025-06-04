package ar.com.manumarcos.kahootclone.microservices.game_session_service.service.impl;

import ar.com.manumarcos.kahootclone.microservices.game_session_service.client.IQuizClient;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.request.GameSessionRequestDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.response.GameSessionResponseDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.exception.GameSessionNotFound;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.exception.GameSessionPinNotFound;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.exception.QuizNotFoundException;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.mapper.gamesession.IGameSessionMapper;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.mapper.quiz.IQuizMapper;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.model.EmbeddedPlayerSession;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.model.EmbeddedQuiz;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.model.GameSession;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.model.GameStatus;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.repository.IGameSessionRepository;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.service.IGameSessionService;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.websocket.dto.request.JoinRequestDTO;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class GameSessionServiceImpl implements IGameSessionService {

    private final IGameSessionRepository gameSessionRepository;
    private final IGameSessionMapper gameSessionMapper;
    private final IQuizMapper quizMapper;
    private final IQuizClient quizClient;

    @Override
    public Page<GameSessionResponseDTO> getAll(Pageable pageable) {
        Page<GameSession> gameSessions =  gameSessionRepository.findAll(pageable);
        return gameSessions.map(gameSessionMapper::toDTO);
    }

    @Override
    public GameSessionResponseDTO save(GameSessionRequestDTO gameSessionRequestDTO) {
        EmbeddedQuiz quiz = this.getQuizById(gameSessionRequestDTO.getQuizId());
        String pin = this.generatePin();
        while(gameSessionRepository.existsByPinAndStatusNotIn(pin,
                List.of(GameStatus.CREATED, GameStatus.IN_PROGRESS))){
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
        if(gameSessionRepository.findById(gameSessionId).isPresent()){
            gameSessionRepository.deleteById(gameSessionId);
        }
        else{
            throw new GameSessionNotFound(gameSessionId);
        }
    }

    @Override
    public void joinGameSession(String pin, JoinRequestDTO joinRequestDTO) {
        Optional<GameSession> gameSessionOpt = gameSessionRepository.findGameSessionByPinAndStatus(pin, GameStatus.CREATED);
        if(gameSessionOpt.isPresent()){
            GameSession gameSession = gameSessionOpt.get();
            EmbeddedPlayerSession playerSession =  EmbeddedPlayerSession.builder()
                    .username(joinRequestDTO.getUsername())
                    .score(0)
                    .build();
            gameSession.getPlayers().add(playerSession);
            gameSessionRepository.save(gameSession);
        }
        else{
            throw new GameSessionPinNotFound(pin);
        }
    }

    private EmbeddedQuiz getQuizById(String id){
        try{
            return quizMapper.toEntity(quizClient.getQuizById(id));
        }catch (FeignException.NotFound e){
            throw new QuizNotFoundException(id);
        }
    }

    private String generatePin(){
        int min = 100_000;
        int max = 999_999;
        return String.valueOf(ThreadLocalRandom.current().nextInt(min, max + 1));
    }
}
