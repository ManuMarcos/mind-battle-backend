package ar.com.manumarcos.kahootclone.microservices.game_session_service.repository.impl;

import ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.ws.AnswerStatsResponseDTO;
import ar.com.manumarcos.kahootclone.microservices.game_session_service.repository.GameSessionCustRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GameSessionCustRepositoryImpl implements GameSessionCustRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<AnswerStatsResponseDTO> getStatsForQuestion(String gameSessionId , String questionId) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("_id").is(gameSessionId)),
                Aggregation.unwind("players"),
                Aggregation.unwind("players.answers"),
                Aggregation.match(Criteria.where("players.answers.questionId").is(questionId)),
                Aggregation.group(
                        Fields.from(
                                Fields.field("optionId", "players.answers.selectedOption._id"),
                                Fields.field("text", "players.answers.selectedOption.text"),
                                Fields.field("correct", "players.answers.selectedOption.isCorrect"),
                                Fields.field("order", "players.answers.selectedOption.order")
                        )
                ).count().as("count"),
                Aggregation.project()
                        .and("_id.optionId").as("optionId")
                        .and("_id.text").as("text")
                        .and("_id.correct").as("correct")
                        .and("_id.order").as("order")
                        .and("count").as("count")
                        .andExclude("_id")
        );
        return mongoTemplate.aggregate(aggregation, "game_sessions", AnswerStatsResponseDTO.class).getMappedResults();
    }
}
