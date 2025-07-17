package ar.com.manumarcos.kahootclone.microservices.game_session_service.repository;

import ar.com.manumarcos.kahootclone.microservices.game_session_service.model.GameSession;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.model.GameStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameSessionRepository extends MongoRepository<GameSession, String> , GameSessionCustRepository {
    boolean existsByPinAndStatusNotIn(String pin, List<GameStatus> status);
    Optional<GameSession> findGameSessionByPinAndStatus(String pin, GameStatus status);
}
