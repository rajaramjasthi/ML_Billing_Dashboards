package com.billingreports.serviceimpl.user;

import com.billingreports.entities.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import com.billingreports.repositories.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser() {
        // Create a user instance
        User user = new User();
        user.setUserName("testUser"); // Use setUserName instead of setUsername
        user.setPassword("password123");

        // Mock the behavior of userRepository.save() to return the user
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Mock the behavior of passwordEncoder.encode() to return the encoded password
        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("encodedPassword");

        // Call the method to be tested
        User savedUser = userServiceImpl.createUser(user);

        // Assert the results
        assertNotNull(savedUser);
        assertEquals("encodedPassword", savedUser.getPassword());

        // Verify method invocations
        verify(userRepository, times(1)).save(any(User.class));
        verify(passwordEncoder, times(1)).encode("password123");
    }

    @Test
    void getAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());

        // Mock the behavior of userService.getAllUsers() to return the list of users
        when(userRepository.findAll()).thenReturn(users);

        // Call the method to be tested
        List<User> actualUsers = userServiceImpl.getAllUsers();

        // Debug logging
        System.out.println("Actual users: " + actualUsers);

        // Assert the result
        assertEquals(2, actualUsers.size(), "Expected number of users does not match actual");
    }



    @Test
    void getUserById() {
        String userId = "user123";
        User mockUser = new User();
        mockUser.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        // Act
        User retrievedUser = userServiceImpl.getUserById(userId);

        // Assert
        assertNotNull(retrievedUser);
        assertEquals(userId, retrievedUser.getId());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void getUserByUsername() {
        String username = "testUser";
        User mockUser = new User();
        mockUser.setUserName(username);

        when(userRepository.findByUserName(username)).thenReturn(Optional.of(mockUser));

        // Act
        User retrievedUser = userServiceImpl.getUserByUsername(username);

        // Assert
        assertNotNull(retrievedUser);
        assertEquals(username, retrievedUser.getUserName());
        verify(userRepository, times(1)).findByUserName(username);
    }

    @Test
    void updateUser() {
        // Arrange
        User existingUser = new User();
        existingUser.setId("1");
        existingUser.setFirstName("Mamatha");
        existingUser.setLastName("Kothakota");
        existingUser.setEmail("kothakotamamatha2001@gmail.com");
        existingUser.setUserName("MamathaKothakota");

        User updatedUser = new User();
        updatedUser.setId("1");
        updatedUser.setFirstName("Rajaram");
        updatedUser.setLastName("Jasthi");
        updatedUser.setEmail("jasthirajaram@gmail.com");
        updatedUser.setUserName("RajaramJasthi");

        when(userRepository.findById("1")).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        // Act
        User returnedUser = userServiceImpl.updateUser(updatedUser);

        // Assert
        assertNotNull(returnedUser);
        assertEquals("Rajaram", returnedUser.getFirstName());
        assertEquals("Jasthi", returnedUser.getLastName());
        assertEquals("jasthirajaram@gmail.com", returnedUser.getEmail());
        assertEquals("RajaramJasthi", returnedUser.getUserName());
        verify(userRepository, times(1)).findById("1");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void deleteUser() {
        User existingUser = new User();
        existingUser.setId("1");
        existingUser.setActive(true);

        when(userRepository.findById("1")).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        // Act
        userServiceImpl.deleteUser("1");

        // Assert
        assertFalse(existingUser.isActive());
        verify(userRepository, times(1)).findById("1");
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void emailOrUserNameAlreadyExist() {
        // Arrange
        User user = new User();
        user.setEmail("test@example.com");
        user.setUserName("testUser");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(userRepository.findByUserName("testUser")).thenReturn(Optional.empty());

        // Act
        boolean result = userServiceImpl.emailOrUserNameAlreadyExist(user);

        // Assert
        assertFalse(result);
        verify(userRepository, times(1)).findByEmail("test@example.com");
        verify(userRepository, times(1)).findByUserName("testUser");
    }

}
