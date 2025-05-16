package com.victor.financial_app.data.service;

import com.victor.financial_app.data.repository.UserRepository;
import com.victor.financial_app.entity.User;
import com.victor.financial_app.presentation.controllers.UserDTO;

import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long createUser(UserDTO userDTO) {

        User entity = new User(
                userDTO.username(),
                userDTO.email(),
                userDTO.password(),
                Instant.now(),
                null);

        User userSaved = userRepository.save(entity);

        return userSaved.getId();
    }
}
