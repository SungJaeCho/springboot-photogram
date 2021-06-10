package com.cos.photogramstart.web.dto.auth;

import lombok.Data;

@Data
public class SignupDto {
    private String usernmae;
    private String password;
    private String email;
    private String name;
}
