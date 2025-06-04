package ar.com.manumarcos.microservices.commons.dto.user;

import lombok.Data;

@Data
public class UserResponseDTO {
    private String id;
    private String username;
    private String email;
}
