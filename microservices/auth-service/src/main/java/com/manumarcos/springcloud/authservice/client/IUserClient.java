package com.manumarcos.springcloud.authservice.client;

import com.manumarcos.springcloud.authservice.dto.RegisterRequestDTO;
import com.manumarcos.springcloud.authservice.dto.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service")
public interface IUserClient {

    @GetMapping("/api/users/internal")
    UserResponseDTO getUserByUsername(@RequestParam String username);

    @PostMapping("/api/users")
    UserResponseDTO create(@RequestBody RegisterRequestDTO registerRequestDTO);
}
