package com.dumbbell.backend.accounts.presentation.controllers;

import com.dumbbell.backend.accounts.application.AccountService;
import com.dumbbell.backend.accounts.domain.aggregates.Account;
import com.dumbbell.backend.accounts.domain.exceptions.InvalidToken;
import com.dumbbell.backend.accounts.presentation.request.LoginRequest;
import com.dumbbell.backend.accounts.presentation.request.RefreshTokenRequest;
import com.dumbbell.backend.accounts.presentation.request.RegisterRequest;
import com.dumbbell.backend.accounts.presentation.responses.LoginResponse;
import com.dumbbell.backend.core.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
        String refreshToken = jwtUtils.generateRefreshToken(account.getId().toString());

        return new LoginResponse(token, refreshToken);
    }

    @PostMapping("/admin/login")
    public LoginResponse loginInAdminPanel(@RequestBody LoginRequest request) {
        Account account = accountService.operatorLogin(request.email, request.password);
        String token = generateToken(account);
        String refreshToken = jwtUtils.generateRefreshToken(account.getId().toString());

        return new LoginResponse(token, refreshToken);
    }

    @DeleteMapping("/auth/logout")
    public ResponseEntity<Object> logout() {
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/auth/refresh")
    public LoginResponse refresh(@RequestBody RefreshTokenRequest request) {
        if (!jwtUtils.validateToken(request.refreshToken)) {
            throw new InvalidToken();
        }
        String id = jwtUtils.extractSubject(request.refreshToken);
        Account account = accountService.findById(id);

        return new LoginResponse(
                generateToken(account),
                jwtUtils.generateRefreshToken(account.getId().toString())
        );
    }

    private String generateToken(Account account) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("role", account.getRole().name());
        return jwtUtils.generateToken(account.getId().toString(), claims);
    }
}
