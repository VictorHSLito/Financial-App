package com.victor.financial_app.data.service;

import com.victor.financial_app.data.repository.UserRepository;
import com.victor.financial_app.entity.User;
import com.victor.financial_app.dtos.CreateUserDTO;

import com.victor.financial_app.dtos.EditUserDTO;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long createUser(CreateUserDTO createUserDTO) {
        //Método que cria um usuário no banco de dados
        User entity = new User(
                createUserDTO.username(),
                createUserDTO.email(),
                createUserDTO.password(),
                Instant.now(),
                null);

        User userSaved = userRepository.save(entity);

        return userSaved.getId();
    }

    public Optional<User> getUserById(Long id) {
        //Método que retorna um usuário no banco de dados pelo id
        var user = userRepository.findById(id);
        return user;
    }

    public List<User> getAllUsers() {
        //Método que lista todos os usuários no banco de dados
        return userRepository.findAll();
    }

    public void updateUserById(Long id, EditUserDTO userDTO) {
        var user = userRepository.findById(id);
        if (user.isPresent()) {
            var entity = user.get();

            if (userDTO.username() != null) {
                entity.setUsername(userDTO.username());
            }

            if (userDTO.password() != null) {
                entity.setPassword(userDTO.password());
            }
            userRepository.save(entity);
        }
    }



    public void deleteUserById(Long id) {
        //Método que deleta um usuário no banco de dados pelo id
        var user = userRepository.findById(id);

        if (user.isPresent()) {
            userRepository.deleteById(user.get().getId());
        }
        else {
            System.out.println("User doesn't exists!");
        }
    }
}
