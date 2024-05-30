package com.indocyber.winterhold.auth.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthTokenRequestDto {
    private final String username;
    private final String password;
}
