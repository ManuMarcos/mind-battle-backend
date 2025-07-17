package ar.com.manumarcos.kahootclone.microservices.game_session_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JoinRequestDTO  {
    private String username;
}
