package com.dumbbell.backend.accounts.presentation.controllers;

import com.dumbbell.backend.accounts.application.AccountService;
import com.dumbbell.backend.accounts.domain.aggregates.Account;
import com.dumbbell.backend.accounts.presentation.request.LoginRequest;
import com.dumbbell.backend.accounts.presentation.request.RegisterRequest;
import com.dumbbell.backend.accounts.presentation.responses.LoginResponse;
import com.dumbbell.backend.core.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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
    public void register(@RequestBody RegisterRequest requestVM) {
        accountService.register(requestVM.email, requestVM.password);
    }

    @GetMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        Account account = accountService.login(request.email, request.password);
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("role", account.getRole().name());
        String token = jwtUtils.generateToken(account.getId().toString(), claims);

        return new LoginResponse(token);
    }
}
