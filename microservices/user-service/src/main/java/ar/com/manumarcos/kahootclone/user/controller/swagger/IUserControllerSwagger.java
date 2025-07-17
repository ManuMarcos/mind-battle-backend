package ar.com.manumarcos.kahootclone.user.controller.swagger;

import ar.com.manumarcos.kahootclone.user.dto.request.UserRequestDTO;
import ar.com.manumarcos.microservices.commons.dto.user.UserInternalResponseDTO;
import ar.com.manumarcos.microservices.commons.dto.user.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Usuarios", description = "Endpoints para gestionar usuarios")
public interface IUserControllerSwagger {

    @Operation(
            summary = "Crear un nuevo usuario",
            description = "Crear un nuevo usuario"
    )
    ResponseEntity<UserInternalResponseDTO> save(@RequestBody UserRequestDTO userRequestDTO);

    @Operation(
            summary = "Obtener todos los usuarios",
            description = "Obtener todos los usuarios"
    )
    ResponseEntity<List<UserResponseDTO>> findAll();


    @Operation(
            summary = "Obtener un usuario por su nombre de usuario",
            description = "Obtener los datos del usuario buscandolo por su nombre de usuario, el mismo debe ser exacto"
    )
    ResponseEntity<UserInternalResponseDTO> findByUsername(@RequestParam String username);

}
