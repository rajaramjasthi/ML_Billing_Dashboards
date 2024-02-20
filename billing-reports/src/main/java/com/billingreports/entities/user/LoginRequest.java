package com.billingreports.entities.user;

import lombok.Data;

@Data
public class LoginRequest {
    private String userName;
    private String password;

}
