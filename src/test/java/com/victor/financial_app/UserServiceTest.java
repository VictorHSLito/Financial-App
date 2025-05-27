package com.victor.financial_app;

import com.victor.financial_app.data.repository.UserRepository;
import com.victor.financial_app.data.service.UserService;
import com.victor.financial_app.dtos.user.CreateUserDTO;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Captor
    private ArgumentCaptor<Long> longArgumentCaptor;

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
            // Arrange
            doThrow(new RuntimeException()).when(userRepository).save(any());
            var input = new CreateUserDTO("User Teste", "emailTeste@gmail.com", "10003D###da");

            // Act & Assert
            assertThrows(RuntimeException.class, () -> userService.createUser(input));
        }
    }

    @Nested
    class GetUserByID {
        @Test
        @DisplayName("Should Get An User By Id Successfully When Optional is Present")
        void shouldGetAnUserSuccessfullyWithOptionalPresent() {
            // Arrange
            User user = new User(12641L,
                    "User",
                    "emailTeste1@gmail.com",
                    "30210##@!4",
                    Instant.now(),
                    null);

            doReturn(Optional.of(user)).when(userRepository).findById(longArgumentCaptor.capture());

            String userId = user.getId().toString();

            var output = userService.getUserById(Long.parseLong(userId));

            assertTrue(output.isPresent());
            assertEquals(user.getId(), longArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("Should not Get An User By Id Successfully When Optional is not Present")
        void shouldNotGetAnUserSuccessfullyWithOptionalNotPresent() {
            // Arrange
            Long userId = new Random().nextLong();

            doReturn(Optional.empty()).when(userRepository).findById(longArgumentCaptor.capture());

            var output = userService.getUserById(userId);

            assertTrue(output.isEmpty());
            assertEquals(userId, longArgumentCaptor.getValue());
        }
    }

    @Nested
    class ListUsers {
        @Test
        @DisplayName("Should Return All Users Successfully")
        void shouldReturnAllUsersWithSuccessfully() {
            // Arrange
            User user = new User();

            var userList = List.of(user);

            doReturn(userList).when(userRepository).findAll();

            // Act
            var output = userService.getAllUsers();

            // Assert
            assertNotNull(output);
            assertEquals(userList.size(), output.size());
        }

        @Test
        @DisplayName("Should Return A Empty List When Users Are Not Found")
        void shouldReturnEmptyListWhenUsersNotFound() {
            // Arrange
            doReturn(Collections.emptyList()).when(userRepository).findAll();

            // Act
            var output = userService.getAllUsers();

            // Assert
            assertNotNull(output);
            assertTrue(output.isEmpty());
            assertEquals(Collections.emptyList(), output);
        }
    }

    @Nested
    class DeleteUser {
        @Test
        @DisplayName("Should Delete An User By Id Successfully")
        void shouldDeleteAnUserByIdSuccessfully() {
            // Arrange
            User user = new User(12641L,
                    "UserTest",
                    "EmailTeste.gmail@gmail.com",
                    "dea3331@@@$$$",
                    Instant.now(),
                    null);

            doReturn(Optional.of(user)).when(userRepository).findById(user.getId()); // Gera o id 0
            doNothing().when(userRepository).deleteById(user.getId());  // Gera o id 1

            // Act
            userService.deleteUserById(user.getId());

            // Assert
            verify(userRepository).findById(longArgumentCaptor.capture());
            verify(userRepository).deleteById(longArgumentCaptor.capture());

            var idList = longArgumentCaptor.getAllValues();

            assertEquals(user.getId(), idList.get(0));
            assertEquals(user.getId(), idList.get(1));
        }

        @Test
        @DisplayName("Should Not Delete An User When User Not Exists")
        void shouldNotDeleteAnUserWhenNotFindId() {
            doReturn(Optional.empty()).when(userRepository).findById(longArgumentCaptor.capture());

            Long id = 12301L;

            userService.deleteUserById(id);

            Long capturedId = longArgumentCaptor.getValue();

            assertEquals(id, capturedId);

            verify(userRepository).findById(id);

            verify(userRepository, times(0)).deleteById(any());
        }
    }

}