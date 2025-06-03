package com.manumarcos.springcloud.authservice.service.impl;

import com.manumarcos.springcloud.authservice.client.IUserClient;
import com.manumarcos.springcloud.authservice.dto.AuthResponseDTO;
import com.manumarcos.springcloud.authservice.dto.LoginRequestDTO;
import com.manumarcos.springcloud.authservice.dto.RegisterRequestDTO;
import com.manumarcos.springcloud.authservice.model.CustomUserDetails;
import com.manumarcos.springcloud.authservice.service.IAuthService;
import com.manumarcos.springcloud.authservice.service.IJwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final IUserClient userClient;
    private final IJwtService jwtService;
    private final AuthenticationManager authenticationManagager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponseDTO register(RegisterRequestDTO registerRequestDTO) {
        registerRequestDTO.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        CustomUserDetails user = new CustomUserDetails(userClient.create(registerRequestDTO));
        return AuthResponseDTO.builder()
                .token(jwtService.getToken(user))
                .build();
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO loginRequestDTO) {
        System.out.println("Username" + loginRequestDTO.getUsername());
        System.out.println("Password" + loginRequestDTO.getPassword());
        authenticationManagager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequestDTO.getUsername(),
                loginRequestDTO.getPassword()));
        UserDetails user = new CustomUserDetails(userClient.getUserByUsername(loginRequestDTO.getUsername()));
        return AuthResponseDTO.builder()
                .token(jwtService.getToken(user))
                .build();
    }
}
