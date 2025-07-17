package com.manumarcos.springcloud.authservice.model;

import ar.com.manumarcos.microservices.commons.dto.user.UserInternalResponseDTO;
import com.manumarcos.springcloud.authservice.dto.UserResponseDTO;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final UserInternalResponseDTO userReponseDTO;

    public String getId(){
        return userReponseDTO.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override
    public String getPassword() {
        return userReponseDTO.getPassword();
    }

    @Override
    public String getUsername() {
        return userReponseDTO.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
