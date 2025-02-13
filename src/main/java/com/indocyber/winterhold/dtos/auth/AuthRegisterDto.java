package com.indocyber.winterhold.dtos.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthRegisterDto {
    private String username;
    private String password;
    private String confirmPassword;
}
