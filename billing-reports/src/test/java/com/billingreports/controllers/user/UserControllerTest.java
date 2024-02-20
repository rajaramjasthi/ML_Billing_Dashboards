package com.billingreports.controllers.user;

import com.billingreports.entities.user.User;
import com.billingreports.jwtUtil.JwtUtil;
import com.billingreports.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtUtil jwtUtil;

    @Test
    void createUser() {
        User mockUser = saveUser();
        when(userService.createUser(any(User.class))).thenReturn(mockUser);
        ResponseEntity<?> responseEntity = userController.createUser(mockUser);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(mockUser, responseEntity.getBody());
        verify(userService).createUser(mockUser);
    }

    @Test
    void getAllUser() {
        List<User> mockUsers = Collections.singletonList(saveUser());
        when(userService.getAllUsers()).thenReturn(mockUsers);
        ResponseEntity<List<User>> responseEntity = userController.getAllUsers();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockUsers, responseEntity.getBody());
        verify(userService).getAllUsers();
    }


    @Test
    void getUserById() {
        User mockUser = new User();
        String mockUserId = "mockUserId";
        when(userService.getUserById(mockUserId)).thenReturn(mockUser);
        ResponseEntity<User> responseEntity = userController.getUserById(mockUserId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockUser, responseEntity.getBody());
        verify(userService).getUserById(mockUserId);
    }

    @Test
    void getUserByUsername() {
        User mockUser = new User();
        String mockUserName = "mockUserName";
        when(userService.getUserByUsername(mockUserName)).thenReturn(mockUser);
        ResponseEntity<User> responseEntity = userController.getUserByUsername(mockUserName);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockUser, responseEntity.getBody());
        verify(userService).getUserByUsername(mockUserName);
    }

    @Test
    void updateUser() {
        User mockUser = new User();
        String mockUserId = "mockUserId";
        User userToUpdate = new User();
        userToUpdate.setId(mockUserId);
        when(userService.updateUser(any(User.class))).thenReturn(mockUser);
        ResponseEntity<User> responseEntity = userController.updateUser(mockUserId, userToUpdate);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockUser, responseEntity.getBody());
        verify(userService).updateUser(userToUpdate);
    }

    @Test
    void deleteUser() {
        String mockUserId = "mockUserId";
        doNothing().when(userService).deleteUser(mockUserId);
        ResponseEntity<String> responseEntity = userController.deleteUser(mockUserId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("User successfully deleted", responseEntity.getBody());
        verify(userService).deleteUser(mockUserId);
    }

    @Test
    void authenticateAndGetToken(){

    }

    public User saveUser() {
        User user = new User();
        user.setUserName("mamatha");
        user.setId("ghjkl;jjj");
        user.setLastName("dfhtgyjkl");
        user.setActive(true);
        user.setEmail("mamatha@gmail.com");
        user.setPassword("mamatha");
        user.setRoles("Admin");
        user.setFirstName("kothakota");
        return user;
    }
}
