package ar.com.manumarcos.kahootclone.user.mapper;

import ar.com.manumarcos.kahootclone.user.dto.UserInternalResponseDTO;
import ar.com.manumarcos.kahootclone.user.dto.UserRequestDTO;
import ar.com.manumarcos.kahootclone.user.dto.UserResponseDTO;
import ar.com.manumarcos.kahootclone.user.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDTO toDto(User user);
    UserInternalResponseDTO toInternalDTO(User user);
    User toEntity(UserRequestDTO userRequestDTO);
}
