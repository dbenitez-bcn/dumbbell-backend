package com.dumbbell.backend.accounts.application;

import com.dumbbell.backend.accounts.domain.aggregates.Account;
import com.dumbbell.backend.accounts.domain.exceptions.EmailAlreadyInUse;
import com.dumbbell.backend.accounts.domain.exceptions.InvalidEmailAddress;
import com.dumbbell.backend.accounts.domain.exceptions.InvalidPasswordFormat;
import com.dumbbell.backend.accounts.domain.exceptions.LoginFailed;
import com.dumbbell.backend.accounts.domain.repositories.AccountRepository;
import com.dumbbell.backend.accounts.domain.valueObjects.AccountEmail;
import com.dumbbell.backend.accounts.domain.valueObjects.HashedPassword;
import com.dumbbell.backend.accounts.domain.valueObjects.PlainPassword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static com.dumbbell.backend.accounts.fixtures.AccountFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class AccountServiceTest {

    private static final Account ACCOUNT = customAccount(ACCOUNT_EMAIL, ACCOUNT_PASSWORD);

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private PasswordManager passwordManager;

    @InjectMocks
    private AccountService sut;

    @Captor
    private ArgumentCaptor<Account> accountCaptor;
    @Captor
    private ArgumentCaptor<PlainPassword> passwordCaptor;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }


    @Test
    void register_shouldCreateANewUser() {
        when(passwordManager.encode(any(PlainPassword.class))).thenReturn(ENCODED_PASSWORD);

        sut.register(ACCOUNT_EMAIL, ACCOUNT_PASSWORD);

        verify(accountRepository).create(accountCaptor.capture());
        Account capturedAccount = accountCaptor.getValue();
        assertThat(capturedAccount.getEmail()
                .getValue()).isEqualTo(ACCOUNT_EMAIL);
        assertThat(capturedAccount.getPassword()
                .getValue()).isEqualTo(ENCODED_PASSWORD);
    }

    @Test
    void register_shouldEncodeThePassword() {
        when(passwordManager.encode(any(PlainPassword.class))).thenReturn(ENCODED_PASSWORD);

        sut.register(ACCOUNT_EMAIL, ACCOUNT_PASSWORD);

        verify(passwordManager).encode(passwordCaptor.capture());
        PlainPassword capturedPassword = passwordCaptor.getValue();
        assertThat(capturedPassword.getValue()).isEqualTo(ACCOUNT_PASSWORD);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "invalid#email.com", "invalid.com", "email@example.c"})
    void register_whenInvalidEmailIsIntroduced_shouldThrowInvalidEmailAddress(String email) {
        assertThatThrownBy(() -> sut.register(email, ACCOUNT_PASSWORD))
                .isInstanceOf(InvalidEmailAddress.class);
    }

    @Test
    void register_whenNodEmailIsIntroduced_shouldThrowInvalidEmailAddress() {
        assertThatThrownBy(() -> sut.register(null, ACCOUNT_PASSWORD))
                .isInstanceOf(InvalidEmailAddress.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "password", "1234", "pass123"})
    void register_whenInvalidPasswordIsIntroduced_shouldThrowInvalidPasswordFormat(String password) {
        assertThatThrownBy(() -> sut.register(ACCOUNT_EMAIL, password))
                .isInstanceOf(InvalidPasswordFormat.class)
                .hasMessage("Should have at least 8 characters and contain numbers and letters");
    }

    @Test
    void register_whenNoPasswordIsIntroduced_shouldThrowInvalidPasswordFormat() {
        assertThatThrownBy(() -> sut.register(ACCOUNT_EMAIL, null))
                .isInstanceOf(InvalidPasswordFormat.class)
                .hasMessage("Should have at least 8 characters and contain numbers and letters");
    }

    @Test
    void register_whenAccountAlreadyExist_shouldThrowEmailAlreadyInUse() {
        willFindAnAccount(defaultAccount());

        assertThatThrownBy(() -> sut.register(ACCOUNT_EMAIL, ACCOUNT_PASSWORD))
                .isInstanceOf(EmailAlreadyInUse.class);

        verify(accountRepository).getByEmail(new AccountEmail(ACCOUNT_EMAIL));
    }

    @Test
    void login_whenCredentialsAreCorrect_shouldLoginTheAccount() {
        willFindAnAccount(ACCOUNT);
        passwordMatch();

        sut.login(ACCOUNT_EMAIL, ACCOUNT_PASSWORD);

        verify(accountRepository).getByEmail(new AccountEmail(ACCOUNT_EMAIL));
        verify(passwordManager).matches(new PlainPassword(ACCOUNT.getPassword().getValue()), ACCOUNT.getPassword());
    }

    @Test
    void login_whenCredentialsAreCorrect_shouldReturnTheAccount() {
        willFindAnAccount(ACCOUNT);
        passwordMatch();

        Account result = sut.login(ACCOUNT_EMAIL, ACCOUNT_PASSWORD);

        assertThat(result).isEqualTo(ACCOUNT);
    }

    @Test
    void login_whenUserNotFound_shouldFailLogin() {
        willFindNoAccount();

        assertThatThrownBy(() -> sut.login(ACCOUNT_EMAIL, ACCOUNT_PASSWORD))
                .isInstanceOf(LoginFailed.class);
    }

    @Test
    void login_whenPasswordDoesNotMatch_shouldFailLogin() {
        willFindAnAccount(defaultAccount());
        passwordsWillNotMatch();

        assertThatThrownBy(() -> sut.login(ACCOUNT_EMAIL, ACCOUNT_PASSWORD))
                .isInstanceOf(LoginFailed.class);
    }

    @Test
    void fails() {
        assertFalse(true);
    }

    private void willFindAnAccount(Account account) {
        when(accountRepository.getByEmail(any(AccountEmail.class))).thenReturn(Optional.of(account));
    }

    private void willFindNoAccount() {
        when(accountRepository.getByEmail(any(AccountEmail.class))).thenReturn(Optional.empty());
    }

    private void passwordMatch() {
        when(passwordManager.matches(any(PlainPassword.class), any(HashedPassword.class))).thenReturn(true);
    }

    private void passwordsWillNotMatch() {
        when(passwordManager.matches(any(PlainPassword.class), any(HashedPassword.class))).thenReturn(false);
    }
}