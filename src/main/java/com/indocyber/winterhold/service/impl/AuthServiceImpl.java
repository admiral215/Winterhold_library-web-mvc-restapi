package com.indocyber.winterhold.service.impl;

import com.indocyber.winterhold.auth.MyPasswordEncoder;
import com.indocyber.winterhold.auth.api.AuthTokenRequestDto;
import com.indocyber.winterhold.auth.api.AuthTokenResponseDto;
import com.indocyber.winterhold.auth.api.jwt.JwtService;
import com.indocyber.winterhold.dtos.account.AccountRegisterRequestDto;
import com.indocyber.winterhold.dtos.account.AccountRegisterResponseDto;
import com.indocyber.winterhold.dtos.auth.AuthRegisterDto;
import com.indocyber.winterhold.entity.Account;
import com.indocyber.winterhold.auth.MyAccountDetail;
import com.indocyber.winterhold.repository.AccountRepository;
import com.indocyber.winterhold.service.AuthService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService, UserDetailsService {
    private final AccountRepository accountRepository;
    private final MyPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(AccountRepository accountRepository, MyPasswordEncoder passwordEncoder, JwtService jwtService) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public void register(AuthRegisterDto dto){
        if (!dto.getPassword().equals(dto.getConfirmPassword())){
            throw new IllegalArgumentException("Password doesnt'match");
        }
        var hashedPassword = passwordEncoder.encode(dto.getPassword());
            accountRepository.save(Account.builder()
                    .username(dto.getUsername())
                    .password(hashedPassword)
                    .build());
    }

    @Override
    public Boolean checkAccount(AuthRegisterDto dto) {
        if (dto.getPassword().equals(dto.getConfirmPassword()) && accountRepository.existsById(dto.getUsername())){
            return false;
        }
        return true;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = accountRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return new MyAccountDetail(user);
    }

//    ----------------------REST------------------------------------
    @Override
    public AuthTokenResponseDto createToken(AuthTokenRequestDto dto) {
        var user = accountRepository.findById(dto.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Username Not Found"));

        if (!passwordEncoder.matches(dto.getPassword(),user.getPassword())){
            throw new IllegalArgumentException("Username or Password is incorrect");
        }
        return AuthTokenResponseDto.builder()
                .token(jwtService.generateToken(user))
                .build();
    }

    @Override
    public AccountRegisterResponseDto registerApi(AccountRegisterRequestDto dto) {
        if (!dto.getPassword().equals(dto.getConfirmPassword())){
            throw new IllegalArgumentException("Password doesnt match");
        }
        if (accountRepository.existsById(dto.getUsername())){
            throw new IllegalArgumentException("Username already taken");
        }

        var user = accountRepository.save(Account.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build());

        return AccountRegisterResponseDto.builder()
                .username(user.getUsername())
                .build();
    }

}
