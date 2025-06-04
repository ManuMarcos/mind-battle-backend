package ar.com.manumarcos.kahootclone.user.mapper;

import ar.com.manumarcos.kahootclone.user.dto.request.UserRequestDTO;
import ar.com.manumarcos.kahootclone.user.model.User;
import ar.com.manumarcos.microservices.commons.dto.user.UserInternalResponseDTO;
import ar.com.manumarcos.microservices.commons.dto.user.UserResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDTO toDto(User user);
    UserInternalResponseDTO toInternalDTO(User user);
    User toEntity(UserRequestDTO userRequestDTO);
}
