package ar.com.manumarcos.kahootclone.user.service;

import ar.com.manumarcos.kahootclone.user.dto.UserInternalResponseDTO;
import ar.com.manumarcos.kahootclone.user.dto.UserRequestDTO;
import ar.com.manumarcos.kahootclone.user.dto.UserResponseDTO;

import java.util.List;

public interface IUserService {

    UserResponseDTO save(UserRequestDTO userRequestDTO);
    List<UserResponseDTO> findAll();
    UserInternalResponseDTO getByUsername(String username);
}
