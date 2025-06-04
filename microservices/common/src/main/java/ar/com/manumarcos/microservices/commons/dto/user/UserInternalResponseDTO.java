package ar.com.manumarcos.microservices.commons.dto.user;

import lombok.Data;

@Data
public class UserInternalResponseDTO {
    private String username;
    private String password;
}
