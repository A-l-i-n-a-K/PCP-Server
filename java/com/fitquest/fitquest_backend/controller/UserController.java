package com.fitquest.fitquest_backend.controller;

import com.fitquest.fitquest_backend.model.SportsmanProfile;
import com.fitquest.fitquest_backend.model.User;
import com.fitquest.fitquest_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user")
    User newUser(@RequestBody User newUser){
        return userRepository.save(newUser);
    }

    @GetMapping("/users")
    List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id){
        try{ return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Профиль не найден для ID: " + id));
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении профиля: " + e.getMessage(), e);
        }
    }
}