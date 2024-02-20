package com.billingreports.entities.user;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users-billing")
public class User {
    @Id
    private String id;
    @NotEmpty(message = "Firstname should not be empty")
    private String firstName;
    @NotEmpty(message = "Lastname should not be empty")
    private String lastName;
    @NotEmpty(message = "Username should not be empty")
    @Indexed(unique = true)
    private String userName;
    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Please enter a valid email address")
    @Indexed(unique = true)
    private String email;
    @NotEmpty(message = "Password should not be empty")
    @Size(min = 6, message = "Password should contain at least 6 characters")
    private String password;
    private String roles;
    private boolean isActive;
}
 