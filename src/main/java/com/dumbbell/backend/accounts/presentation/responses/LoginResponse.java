package com.dumbbell.backend.accounts.presentation.responses;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginResponse {
    public final String token;
    public final String refreshToken;
}
