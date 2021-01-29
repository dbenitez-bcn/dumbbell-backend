package com.dumbbell.backend.accounts.presentation.controllers;

import com.dumbbell.backend.accounts.application.AccountService;
import com.dumbbell.backend.accounts.domain.aggregates.Account;
import com.dumbbell.backend.accounts.presentation.request.LoginRequest;
import com.dumbbell.backend.accounts.presentation.request.RegisterRequest;
import com.dumbbell.backend.accounts.presentation.responses.LoginResponse;
import com.dumbbell.backend.core.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RequiredArgsConstructor
@RestController
public class AccountController {
    private final AccountService accountService;
    private final JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequest requestVM) {
        accountService.register(requestVM.email, requestVM.password);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        Account account = accountService.login(request.email, request.password);
        String token = generateToken(account);

        return new LoginResponse(token);
    }

    public LoginResponse adminLogin(LoginRequest request) {
        Account account = accountService.operatorLogin(request.email, request.password);
        String token = generateToken(account);

        return new LoginResponse(token);
    }

    private String generateToken(Account account) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("role", account.getRole().name());
        return jwtUtils.generateToken(account.getId().toString(), claims);
    }
}
