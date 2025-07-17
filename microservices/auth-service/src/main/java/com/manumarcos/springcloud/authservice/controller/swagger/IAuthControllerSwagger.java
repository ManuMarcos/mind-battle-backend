package com.manumarcos.springcloud.authservice.controller.swagger;

import com.manumarcos.springcloud.authservice.dto.AuthResponseDTO;
import com.manumarcos.springcloud.authservice.dto.LoginRequestDTO;
import com.manumarcos.springcloud.authservice.dto.RegisterRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Auth", description = "Endpoints para gestionar la autenticación")
public interface IAuthControllerSwagger {

    @Operation(
            summary = "Loguear un usuario",
            description = "Endpoint para hacer login de un usuario proporcionando el username y el password, se retorna el JWT"
    )
    ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO);

    @Operation(
            summary = "Registrar un nuevo usuario",
            description = "Endpoint para registrar un nuevo usuario proporcionando el username, email y password, se retorna el JWT"
    )
    ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterRequestDTO registerRequestDTO);
}
