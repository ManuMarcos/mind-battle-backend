package com.manumarcos.springcloud.authservice.controller;


import com.manumarcos.springcloud.authservice.dto.AuthResponseDTO;
import com.manumarcos.springcloud.authservice.dto.LoginRequestDTO;
import com.manumarcos.springcloud.authservice.dto.RegisterRequestDTO;
import com.manumarcos.springcloud.authservice.dto.UserResponseDTO;
import com.manumarcos.springcloud.authservice.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final IAuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO){
        System.out.println("Hola");
        return ResponseEntity.ok(authService.login(loginRequestDTO));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterRequestDTO registerRequestDTO){
        return ResponseEntity.ok(authService.register(registerRequestDTO));
    }

}
