package com.manumarcos.springcloud.authservice.service;


import org.springframework.security.core.userdetails.UserDetails;

public interface IJwtService {
    String extractUsername(String token);
    boolean isTokenValid(String token, UserDetails userDetails);
    String getToken(UserDetails user);
}
