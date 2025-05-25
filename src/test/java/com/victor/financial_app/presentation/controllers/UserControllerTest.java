package com.victor.financial_app.presentation.controllers;

import com.victor.financial_app.data.repository.UserRepository;
import com.victor.financial_app.data.service.UserService;
import com.victor.financial_app.dtos.CreateUserDTO;
import com.victor.financial_app.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Nested
    class CreateUser {

        @Test
        @DisplayName("Should Create a User with Success")
        void shouldCreateAUser() {
            // Arrange
            User user = new User(1231L, "User Teste", "emailTeste@gmail.com", "10003D###da", Instant.now(), null);

            doReturn(user).when(userRepository).save(userArgumentCaptor.capture());
            var input = new CreateUserDTO("User Teste", "emailTeste@gmail.com", "10003D###da");

            // Act
            var output = userService.createUser(input);

            var userCaptured = userArgumentCaptor.getValue();



            // Assert
            assertNotNull(output);
            assertEquals(input.username(), userCaptured.getUsername());
            assertEquals(input.email(), userCaptured.getEmail());
            assertEquals(input.password(), userCaptured.getPassword());
        }


        @Test
        @DisplayName("Should Throw Exception When Error Occurs")
        void shouldThrowExceptionWhenErrorOccurs() {
            doThrow(new RuntimeException()).when(userRepository).save(any());
            var input = new CreateUserDTO("User Teste", "emailTeste@gmail.com", "10003D###da");

            // Act
            assertThrows(RuntimeException.class, () -> userService.createUser(input));
        }
    }

    @Nested
    class GetUserByID {
        @Test
        @DisplayName("Should Get An User By Id Successfully")
        void shouldGetAnUserSuccessfully() {

        }
    }
}