package com.indocyber.winterhold.auth.api;

import com.indocyber.winterhold.dtos.account.AccountRegisterRequestDto;
import com.indocyber.winterhold.dtos.account.AccountRegisterResponseDto;
import com.indocyber.winterhold.entity.Account;
import com.indocyber.winterhold.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class RestAuthController {
    private final AuthService authService;

    public RestAuthController(AuthService authRestService) {
        this.authService = authRestService;
    }

    @PostMapping("")
    public ResponseEntity<AuthTokenResponseDto> createToken(@RequestBody AuthTokenRequestDto dto){
        return ResponseEntity.ok(authService.createToken(dto));
    }

    @PostMapping("register")
    public ResponseEntity<AccountRegisterResponseDto> registerApi (@RequestBody AccountRegisterRequestDto dto){
        return ResponseEntity.ok(authService.registerApi(dto));
    }
}
