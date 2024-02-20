package com.billingreports.service.user;

import com.billingreports.entities.user.User;

import java.util.List;

public interface UserService{
    User createUser(User user);

    List<User> getAllUsers();

    User getUserById(String userId);

    User getUserByUsername(String userName);

    User updateUser(User user);

    void deleteUser(String userId);

    boolean emailOrUserNameAlreadyExist(User user);

    public String changePassword(String oldPassword, String newPassword);
}
