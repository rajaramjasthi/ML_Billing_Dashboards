package com.billingreports.controllers.user;

import com.billingreports.entities.user.LoginRequest;
import com.billingreports.entities.user.User;
import com.billingreports.exceptions.UsernameOrEmailAlreadyExistsException;
import com.billingreports.exceptions.UsernameOrPasswordIncorrectException;
import com.billingreports.jwtUtil.JwtUtil;
import com.billingreports.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")//working
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            // Attempt to create a user
            User savedUser = userService.createUser(user);
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        } catch (UsernameOrEmailAlreadyExistsException e) {
            // Username already exists - handle this exception
            return new ResponseEntity<>("Username already exists", HttpStatus.CONFLICT);
        } catch (Exception e) {
            // Other unexpected exceptions
            return new ResponseEntity<>("Failed to create user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getallusers")//working
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/getuserbyid/{id}")//working
    @PreAuthorize("hasAuthority('ROLE_USER')")//working
    public ResponseEntity<User> getUserById(@PathVariable("id") String userId) {
        User user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/getuserbyname/{username}")//working
    @PreAuthorize("hasAuthority('ROLE_USER')")//working
    public ResponseEntity<User> getUserByUsername(@PathVariable("username") String userName) {
        User user = userService.getUserByUsername(userName);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/updateuser/{id}")//username//done
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<User> updateUser(@PathVariable("id") String userId, @RequestBody User user) {
        user.setId(userId);
        User updatedUser = userService.updateUser(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/deleteuser/{id}")//working
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> deleteUser(@PathVariable("id") String userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>("User successfully deleted", HttpStatus.OK);
    }

    @PutMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestParam String oldPassword ,@RequestParam String newPassword) throws Exception {
        return new ResponseEntity<>(userService.changePassword(oldPassword,newPassword),HttpStatus.ACCEPTED);
    }

    @PostMapping("/token")
    public String authenticateAndGetToken(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            String res = jwtUtil.generateToken(loginRequest.getUserName());
            String result = "{\"token\":\"" + res + "\"}";
            return result;
        } else {
            throw new UsernameOrPasswordIncorrectException();
        }

    }
}
