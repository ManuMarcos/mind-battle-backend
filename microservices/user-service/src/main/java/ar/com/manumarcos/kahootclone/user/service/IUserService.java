package ar.com.manumarcos.kahootclone.user.service;

import ar.com.manumarcos.kahootclone.user.dto.request.UserRequestDTO;
import ar.com.manumarcos.microservices.commons.dto.user.UserInternalResponseDTO;
import ar.com.manumarcos.microservices.commons.dto.user.UserResponseDTO;

import java.util.List;

public interface IUserService {

    UserResponseDTO save(UserRequestDTO userRequestDTO);
    List<UserResponseDTO> findAll();
    UserInternalResponseDTO getByUsername(String username);
}
