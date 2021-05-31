package com.dumbbell.backend.accounts.presentation.request;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RefreshTokenRequest {
    public final String refreshToken;
}
