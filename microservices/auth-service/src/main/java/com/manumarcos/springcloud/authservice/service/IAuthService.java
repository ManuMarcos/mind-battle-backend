package com.manumarcos.springcloud.authservice.service;

import com.manumarcos.springcloud.authservice.dto.LoginRequestDTO;
import com.manumarcos.springcloud.authservice.dto.RegisterRequestDTO;
import com.manumarcos.springcloud.authservice.dto.AuthResponseDTO;

public interface IAuthService {

    AuthResponseDTO register(RegisterRequestDTO registerRequestDTO);
    AuthResponseDTO login(LoginRequestDTO loginRequestDTO);
}
