package com.indocyber.winterhold.dtos.account;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountRegisterRequestDto {
    private final String username;
    private final String password;
    private final String confirmPassword;
}
