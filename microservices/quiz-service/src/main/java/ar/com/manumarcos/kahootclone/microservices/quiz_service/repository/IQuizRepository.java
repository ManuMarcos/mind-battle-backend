package ar.com.manumarcos.kahootclone.microservices.quiz_service.repository;

import ar.com.manumarcos.kahootclone.microservices.quiz_service.model.Quiz;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IQuizRepository extends MongoRepository<Quiz, String> {


}
