package com.example.interview.controller;

import com.example.interview.dto.LoginDTO;
import com.example.interview.dto.LoginResponseDTO;
import com.example.interview.dto.UserDTO;
import com.example.interview.dto.UserResponseDTO;
import com.example.interview.entity.User;
import com.example.interview.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.net.Authenticator;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService){
        this.userService=userService;
    }
    @GetMapping
    public List<UserResponseDTO> getAllUsers(){
        return userService.getAllUsers();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return "User deleted";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id,@Valid @RequestBody UserDTO dto){
        return userService.updateUser(id,dto);
    }
    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }
    @PostMapping
    public UserResponseDTO createUser(@Valid @RequestBody UserDTO userDTO){
        return userService.saveUser(userDTO);
    }
    @GetMapping("/me")
    public String getCurrentUser(Authentication auth){
        return auth.getName();
    }
    @GetMapping("/me/details")
    public UserResponseDTO getMyDetails(Authentication auth){
        return userService.getByEmail(auth.getName());
    }
    @PostMapping("/login")
    public LoginResponseDTO login(@Valid @RequestBody LoginDTO dto){
        return userService.login(dto);
    }
}
