package com.manumarcos.springcloud.authservice.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface ICustomUserDetailsService {
    
    UserDetails loadByUsername(String email);
}
