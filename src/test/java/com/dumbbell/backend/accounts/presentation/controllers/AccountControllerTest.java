package com.dumbbell.backend.accounts.presentation.controllers;

import com.dumbbell.backend.accounts.application.AccountService;
import com.dumbbell.backend.accounts.domain.aggregates.Account;
import com.dumbbell.backend.accounts.presentation.request.LoginRequest;
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

import static com.dumbbell.backend.accounts.AccountFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
}