package com.indocyber.winterhold.dtos.account;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountRegisterResponseDto {
    private final String username;
}
