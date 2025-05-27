package com.victor.financial_app.controllers;

import com.victor.financial_app.data.repository.UserRepository;
import com.victor.financial_app.data.service.UserService;
import com.victor.financial_app.dtos.account.AccountResponseDTO;
import com.victor.financial_app.dtos.account.CreateAccountDTO;
import com.victor.financial_app.dtos.user.CreateUserDTO;
import com.victor.financial_app.dtos.user.EditUserDTO;
import com.victor.financial_app.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private UserService userService;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserDTO createUserDTO) {
        //Endpoint responsável pela criação de um usuário
        Long id = userService.createUser(createUserDTO);
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

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable("id") String id, @RequestBody EditUserDTO userDTO) {
        //Endpoint responsável por atualizar um usuário
        userService.updateUserById(Long.parseLong(id), userDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable("id") String id){
        //Endpoint responsável por deletar um usuário pelo id
        userService.deleteUserById(Long.parseLong(id));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/accounts")
    public ResponseEntity<Void> createAccount(@PathVariable ("userId") String id,
                                                 @RequestBody CreateAccountDTO accountDTO) {
        userService.createAccount(Long.parseLong(id), accountDTO);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/accounts")
    public ResponseEntity<List<AccountResponseDTO>> getAllAccounts(@PathVariable ("userId") String id) {

        return ResponseEntity.ok(userService.getAllAccounts(id));
    }
}

