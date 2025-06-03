package com.manumarcos.springcloud.authservice.service.impl;

import com.manumarcos.springcloud.authservice.client.IUserClient;
import com.manumarcos.springcloud.authservice.dto.UserResponseDTO;
import com.manumarcos.springcloud.authservice.model.CustomUserDetails;
import com.manumarcos.springcloud.authservice.service.ICustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private final IUserClient userClient;

    public CustomUserDetailsServiceImpl(IUserClient userClient){
        this.userClient = userClient;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserResponseDTO user = userClient.getUserByUsername(username);
        return new CustomUserDetails(user);
    }
}
