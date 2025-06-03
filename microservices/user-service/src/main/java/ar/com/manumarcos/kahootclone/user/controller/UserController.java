package ar.com.manumarcos.kahootclone.user.controller;

import ar.com.manumarcos.kahootclone.user.dto.UserInternalResponseDTO;
import ar.com.manumarcos.kahootclone.user.dto.UserRequestDTO;
import ar.com.manumarcos.kahootclone.user.dto.UserResponseDTO;
import ar.com.manumarcos.kahootclone.user.service.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService IUserService){
        this.userService = IUserService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> save(@RequestBody UserRequestDTO userRequestDTO){
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
