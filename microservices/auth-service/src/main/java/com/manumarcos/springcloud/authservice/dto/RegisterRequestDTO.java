package com.manumarcos.springcloud.authservice.dto;


import lombok.Data;

@Data
public class RegisterRequestDTO {

    private String email;
    private String username;
    private String password;
}
