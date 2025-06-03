package com.manumarcos.springcloud.authservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponseDTO {
    String token;
}
