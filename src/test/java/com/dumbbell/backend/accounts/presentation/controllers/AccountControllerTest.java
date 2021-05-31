package com.dumbbell.backend.accounts.presentation.controllers;

import com.dumbbell.backend.accounts.application.AccountService;
import com.dumbbell.backend.accounts.domain.aggregates.Account;
import com.dumbbell.backend.accounts.domain.exceptions.InvalidToken;
import com.dumbbell.backend.accounts.presentation.request.LoginRequest;
import com.dumbbell.backend.accounts.presentation.request.RefreshTokenRequest;
import com.dumbbell.backend.accounts.presentation.request.RegisterRequest;
import com.dumbbell.backend.accounts.presentation.responses.LoginResponse;
import com.dumbbell.backend.core.utils.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.UUID;

import static com.dumbbell.backend.accounts.AccountFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class AccountControllerTest {
    @Mock
    private AccountService accountService;
    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private AccountController sut;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void register_shouldRegisterAnAccount() {
        RegisterRequest request = new RegisterRequest(ACCOUNT_EMAIL, ACCOUNT_PASSWORD);

        sut.register(request);

        verify(accountService).register(ACCOUNT_EMAIL, ACCOUNT_PASSWORD);
    }

    @Test
    void login_shouldLoginAnAccount() {
        String aToken = "A_TOKEN";
        Account account = defaultAccount();
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("role", account.getRole().name());
        when(jwtUtils.generateToken(account.getId().toString(), claims)).thenReturn(aToken);
        when(accountService.login(ACCOUNT_EMAIL, ACCOUNT_PASSWORD)).thenReturn(account);

        LoginResponse result = sut.login(new LoginRequest(ACCOUNT_EMAIL, ACCOUNT_PASSWORD));

        assertThat(result.token).isEqualTo(aToken);
    }

    @Test
    void login_shouldReturnARefreshToken() {
        String aRefreshToken = "REFRESH_TOKEN";
        Account account = defaultAccount();
        when(accountService.login(ACCOUNT_EMAIL, ACCOUNT_PASSWORD)).thenReturn(account);
        when(jwtUtils.generateRefreshToken(account.getId().toString())).thenReturn(aRefreshToken);

        LoginResponse result = sut.login(new LoginRequest(ACCOUNT_EMAIL, ACCOUNT_PASSWORD));

        assertThat(result.refreshToken).isEqualTo(aRefreshToken);
    }

    @Test
    void loginInAdminPanel_shouldLoginAnAccount() {
        String aToken = "A_TOKEN";
        Account account = defaultAccount();
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("role", account.getRole().name());
        when(jwtUtils.generateToken(account.getId().toString(), claims)).thenReturn(aToken);
        when(accountService.operatorLogin(ACCOUNT_EMAIL, ACCOUNT_PASSWORD)).thenReturn(account);

        LoginResponse result = sut.loginInAdminPanel(new LoginRequest(ACCOUNT_EMAIL, ACCOUNT_PASSWORD));

        assertThat(result.token).isEqualTo(aToken);
    }

    @Test
    void loginInAdminPane_shouldReturnARefreshToken() {
        String aRefreshToken = "REFRESH_TOKEN";
        Account account = defaultAccount();
        when(accountService.operatorLogin(ACCOUNT_EMAIL, ACCOUNT_PASSWORD)).thenReturn(account);
        when(jwtUtils.generateRefreshToken(account.getId().toString())).thenReturn(aRefreshToken);

        LoginResponse result = sut.loginInAdminPanel(new LoginRequest(ACCOUNT_EMAIL, ACCOUNT_PASSWORD));

        assertThat(result.refreshToken).isEqualTo(aRefreshToken);
    }

    @Test
    void logout_shouldReturnTheRightStatusCode() {
        ResponseEntity<Object> result = sut.logout();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void refresh_whenRefreshTokenIsValid_shouldReturnANewToken() {
        String uuid = UUID.randomUUID().toString();
        String refreshToken = "REFRESH_TOKEN";
        String newToken = "NEW_TOKEN";
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("role", ACCOUNT.getRole().name());
        when(accountService.findById(uuid)).thenReturn(ACCOUNT);
        when(jwtUtils.extractSubject(refreshToken)).thenReturn(uuid);
        when(jwtUtils.generateToken(ACCOUNT.getId().toString(), claims)).thenReturn(newToken);
        when(jwtUtils.validateToken(refreshToken)).thenReturn(true);

        LoginResponse got = sut.refresh(new RefreshTokenRequest(refreshToken));

        assertThat(got.token).isEqualTo(newToken);
    }

    @Test
    void refresh_whenRefreshTokenIsValid_shouldReturnANewRefreshToken() {
        String uuid = UUID.randomUUID().toString();
        String refreshToken = "REFRESH_TOKEN";
        String newRefreshToken = "NEW_REFRESH_TOKEN";
        when(accountService.findById(uuid)).thenReturn(ACCOUNT);
        when(jwtUtils.extractSubject(refreshToken)).thenReturn(uuid);
        when(jwtUtils.generateRefreshToken(ACCOUNT.getId().toString())).thenReturn(newRefreshToken);
        when(jwtUtils.validateToken(refreshToken)).thenReturn(true);

        LoginResponse got = sut.refresh(new RefreshTokenRequest(refreshToken));

        assertThat(got.refreshToken).isEqualTo(newRefreshToken);
    }

    @Test
    void refresh_whenRefreshTokenIsNotValid_shouldDoNothing() {
        String refreshToken = "REFRESH_TOKEN";
        when(jwtUtils.validateToken(anyString())).thenReturn(false);

        assertThatThrownBy(() -> sut.refresh(new RefreshTokenRequest(refreshToken)))
                .isInstanceOf(InvalidToken.class);

        verifyZeroInteractions(accountService);
        verify(jwtUtils, never()).extractSubject(anyString());
        verify(jwtUtils, never()).generateRefreshToken(anyString());
        verify(jwtUtils, never()).generateToken(anyString(), anyMap());
    }
}