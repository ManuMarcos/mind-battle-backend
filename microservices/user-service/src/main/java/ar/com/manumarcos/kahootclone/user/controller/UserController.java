package ar.com.manumarcos.kahootclone.user.controller;

import ar.com.manumarcos.kahootclone.user.controller.swagger.IUserControllerSwagger;
import ar.com.manumarcos.kahootclone.user.dto.request.UserRequestDTO;
import ar.com.manumarcos.kahootclone.user.service.IUserService;
import ar.com.manumarcos.microservices.commons.dto.user.UserInternalResponseDTO;
import ar.com.manumarcos.microservices.commons.dto.user.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController implements IUserControllerSwagger {

    private final IUserService userService;

    @PostMapping
    public ResponseEntity<UserInternalResponseDTO> save(@RequestBody UserRequestDTO userRequestDTO){
        System.out.println(userRequestDTO);
        return ResponseEntity.ok(userService.save(userRequestDTO));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll(){
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/internal")
    public ResponseEntity<UserInternalResponseDTO> findByUsername(@RequestParam String username){
        return ResponseEntity.ok(userService.getByUsername(username));
    }

}
