package com.indocyber.winterhold.service;

import com.indocyber.winterhold.auth.api.AuthTokenRequestDto;
import com.indocyber.winterhold.auth.api.AuthTokenResponseDto;
import com.indocyber.winterhold.dtos.account.AccountRegisterRequestDto;
import com.indocyber.winterhold.dtos.account.AccountRegisterResponseDto;
import com.indocyber.winterhold.dtos.auth.AuthRegisterDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AuthService {
    void register(AuthRegisterDto dto);

    Boolean checkAccount(AuthRegisterDto dto);
    AuthTokenResponseDto createToken(AuthTokenRequestDto dto);

    //    Authentication.
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    AccountRegisterResponseDto registerApi(AccountRegisterRequestDto dto);
}
