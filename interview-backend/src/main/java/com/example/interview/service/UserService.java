package com.example.interview.service;

import com.example.interview.dto.LoginDTO;
import com.example.interview.dto.LoginResponseDTO;
import com.example.interview.dto.UserDTO;
import com.example.interview.dto.UserResponseDTO;
import com.example.interview.entity.User;
import com.example.interview.exception.ResourceNotFoundException;
import com.example.interview.repository.UserRepository;
import com.example.interview.util.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repo;
    private final BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
    public UserService(UserRepository repo){
        this.repo=repo;
    }
    public UserResponseDTO saveUser(UserDTO dto){
        User user=new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(encoder.encode(dto.getPassword()));
        user.setRole("USER");
        User saved=repo.save(user);
        return convertToDTO(saved);
    }
    public List<UserResponseDTO> getAllUsers(){

        List<User> users=repo.findAll();
        return users.stream().map(user -> {
            UserResponseDTO dto=new UserResponseDTO();
            dto.setId(user.getId());
            dto.setName(user.getName());
            dto.setEmail(user.getEmail());
            return dto;
        }).toList();
    }
    public void deleteUser(Long id){
        repo.deleteById(id);
    }
    public UserResponseDTO getUserById(Long id){
        User user = repo.findById(id)
                .orElseThrow(() ->new ResourceNotFoundException("User Not Found with id "+id));
        return convertToDTO(user);
    }
    public User updateUser(Long id,UserDTO dto){
        User user=repo.findById(id)
                .orElseThrow(() ->new ResourceNotFoundException("User not found"));
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(encoder.encode(dto.getPassword()));
        return repo.save(user);
    }
    public UserResponseDTO convertToDTO(User user){
        UserResponseDTO dto=new UserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }
    public UserResponseDTO getByEmail(String email){
        User user=repo.findByEmail(email)
                .orElseThrow(()->new RuntimeException("User not found"));
        return convertToDTO(user);
    }
    public LoginResponseDTO login(LoginDTO dto){
        User user=repo.findByEmail(dto.getEmail())
                .orElseThrow(()-> new RuntimeException("User not found"));
        if(encoder.matches(dto.getPassword(), user.getPassword())){
            String token=JwtUtil.generateToken(user.getEmail(),user.getRole());
            LoginResponseDTO res=new LoginResponseDTO();
            res.setToken(token);
            res.setEmail(user.getEmail());
            res.setRole(user.getRole());
            return res;
        }
        else{
            throw new RuntimeException("Invalid Password");
        }
    }
}
