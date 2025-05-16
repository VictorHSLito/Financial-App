package com.victor.financial_app.presentation.controllers;

import com.victor.financial_app.data.service.UserService;
import com.victor.financial_app.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) {
        Long id = userService.createUser(userDTO);
        return ResponseEntity.created(URI.create("/users/" + id.toString())).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") String id) {
        return null;
    }
}

