package ar.com.manumarcos.kahootclone.user.service.impl;

import ar.com.manumarcos.kahootclone.user.dto.request.UserRequestDTO;
import ar.com.manumarcos.kahootclone.user.exception.UserNotFoundException;
import ar.com.manumarcos.kahootclone.user.exception.UsernameAlreadyExists;
import ar.com.manumarcos.kahootclone.user.mapper.UserMapper;
import ar.com.manumarcos.kahootclone.user.model.User;
import ar.com.manumarcos.kahootclone.user.repository.IUserRepository;
import ar.com.manumarcos.kahootclone.user.service.IUserService;
import ar.com.manumarcos.microservices.commons.dto.user.UserInternalResponseDTO;
import ar.com.manumarcos.microservices.commons.dto.user.UserResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    private final IUserRepository IUserRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(IUserRepository IUserRepository, UserMapper userMapper) {
        this.IUserRepository = IUserRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponseDTO save(UserRequestDTO userRequestDTO) {
        if(IUserRepository.existsByUsername(userRequestDTO.getUsername())){
            throw new UsernameAlreadyExists(userRequestDTO.getUsername());
        }
        User user = userMapper.toEntity(userRequestDTO);
        System.out.println(user.toString());
        return userMapper.toDto(IUserRepository.save(user));
    }

    @Override
    public List<UserResponseDTO> findAll() {
        return IUserRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public UserInternalResponseDTO getByUsername(String username) {
        Optional<User> userOptional = IUserRepository.findByUsername(username);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException(username);
        }
        return userMapper.toInternalDTO(userOptional.get());
    }


}
