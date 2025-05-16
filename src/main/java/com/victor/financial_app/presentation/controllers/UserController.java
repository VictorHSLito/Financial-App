package com.victor.financial_app.presentation.controllers;

import com.victor.financial_app.data.service.UserService;
import com.victor.financial_app.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) {
        //Endpoint responsável pela criação de um usuário
        Long id = userService.createUser(userDTO);
        return ResponseEntity.created(URI.create("/users/" + id.toString())).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") String id) {
        //Endpoint responsável por retornar ou não um usuário
        var user = userService.getUserById(Long.parseLong(id));
        if (user.isPresent()) {
            var u = user.get();
            return ResponseEntity.ok(u);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getUsers() {
        //Endpoint responsável por retornar todos os usuários
        return ResponseEntity.ok(userService.getAllUsers());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable("id") String id){
        //Endpoint responsável por deletar um usuário pelo id
        userService.deleteUserById(Long.parseLong(id));
        return ResponseEntity.noContent().build();
    }
}

