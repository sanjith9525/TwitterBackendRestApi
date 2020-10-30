package com.example.twitterbackend.service;

import com.example.twitterbackend.dto.UserResponse;
import com.example.twitterbackend.exceptions.TwitterException;
import com.example.twitterbackend.mapper.UserMapper;
import com.example.twitterbackend.model.User;
import com.example.twitterbackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class UserService {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserResponse getCurrentUser() {
        return userMapper.mapToDto(authService.getCurrentUser());
    }

    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new TwitterException("User not found with ID - "+id));
        return userMapper.mapToDto(user);
    }

    @Transactional(readOnly = true)
    public UserResponse getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new TwitterException("User not found with handle "+username));
        return userMapper.mapToDto(user);
    }
}
