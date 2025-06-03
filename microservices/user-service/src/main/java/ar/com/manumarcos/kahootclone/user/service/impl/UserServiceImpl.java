package ar.com.manumarcos.kahootclone.user.service.impl;

import ar.com.manumarcos.kahootclone.user.dto.UserInternalResponseDTO;
import ar.com.manumarcos.kahootclone.user.dto.UserRequestDTO;
import ar.com.manumarcos.kahootclone.user.dto.UserResponseDTO;
import ar.com.manumarcos.kahootclone.user.exception.UserNotFoundException;
import ar.com.manumarcos.kahootclone.user.exception.UsernameAlreadyExists;
import ar.com.manumarcos.kahootclone.user.mapper.UserMapper;
import ar.com.manumarcos.kahootclone.user.model.User;
import ar.com.manumarcos.kahootclone.user.repository.UserRepository;
import ar.com.manumarcos.kahootclone.user.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponseDTO save(UserRequestDTO userRequestDTO) {
        if(userRepository.existsByUsername(userRequestDTO.getUsername())){
            throw new UsernameAlreadyExists(userRequestDTO.getUsername());
        }
        User user = userMapper.toEntity(userRequestDTO);
        System.out.println(user.toString());
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public List<UserResponseDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public UserInternalResponseDTO getByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException(username);
        }
        return userMapper.toInternalDTO(userOptional.get());
    }


}
