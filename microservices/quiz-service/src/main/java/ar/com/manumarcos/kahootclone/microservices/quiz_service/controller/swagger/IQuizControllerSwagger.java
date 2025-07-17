package ar.com.manumarcos.kahootclone.microservices.quiz_service.controller.swagger;


import ar.com.manumarcos.kahootclone.microservices.quiz_service.dto.request.QuizRequestDTO;
import ar.com.manumarcos.microservices.commons.dto.quiz.QuizResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name ="Quizzes", description = "Endpoints para gestionar los cuestionarios")
public interface IQuizControllerSwagger {

    @Operation(
            summary = "Obtener todos los cuestionarios",
            description = "Endpoint para obtener todos los cuestionarios paginados"
    )
    ResponseEntity<Page<QuizResponseDTO>> getAll(Pageable pageable);

    @Operation(
            summary = "Crear un cuestionario",
            description = "Endpoint para un nuevo cuestionario"
    )
    ResponseEntity<String> create(@RequestBody QuizRequestDTO quizRequestDTO);

    @Operation(
            summary = "Obtener un cuestionario por id",
            description = "Endpoint para obtener un cuestionario buscandolo por su identificador (id)"
    )
    ResponseEntity<QuizResponseDTO> findById(@PathVariable String quizId);

    @Operation(
            summary = "Eliminar un cuestionario por id",
            description = "Endpoint para eliminar un cuestionario por su identificador (id)"
    )
    ResponseEntity<String> deleteById(@PathVariable String quizId);
}
