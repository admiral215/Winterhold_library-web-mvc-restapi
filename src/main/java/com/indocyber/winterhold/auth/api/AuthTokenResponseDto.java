package com.indocyber.winterhold.auth.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthTokenResponseDto {
    private String token;
}
