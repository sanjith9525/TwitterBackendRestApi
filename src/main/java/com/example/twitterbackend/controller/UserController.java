package com.example.twitterbackend.controller;

import com.example.twitterbackend.dto.UserResponse;
import com.example.twitterbackend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    public final UserService userService;
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return status(HttpStatus.OK).body(userService.getAllUsers());
    }

    @GetMapping("/current")
    public ResponseEntity<UserResponse> getCurrentUser() {
        return status(HttpStatus.OK).body(userService.getCurrentUser());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return status(HttpStatus.OK).body(userService.getUserById(id));
    }

    @GetMapping("/by-handle/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable String username) {
        return status(HttpStatus.OK).body(userService.getUserByUsername(username));
    }
}
