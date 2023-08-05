package com.dumbbell.backend.accounts.infrastructure.postgresql.implementations;

import com.dumbbell.backend.accounts.domain.aggregates.Account;
import com.dumbbell.backend.accounts.domain.valueObjects.AccountEmail;
import com.dumbbell.backend.accounts.infrastructure.postgresql.entities.AccountEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;
import java.util.UUID;

import static com.dumbbell.backend.accounts.AccountEntityFixture.defaultAccountEntity;
import static com.dumbbell.backend.accounts.AccountFixture.ACCOUNT_EMAIL;
import static com.dumbbell.backend.accounts.AccountFixture.defaultAccount;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class PostgresAccountRepositoryTest {

    private static final AccountEntity ACCOUNT_ENTITY = defaultAccountEntity();

    @Mock
    private AccountDataSource accountDataSource;

    @InjectMocks
    private PostgresAccountRepository sut;

    @Captor
    private ArgumentCaptor<AccountEntity> accountCaptor;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void create_shouldCreateANewUser() {
        Account account = defaultAccount();

        sut.create(account);

        verify(accountDataSource).save(accountCaptor.capture());
        AccountEntity capturedAccount = accountCaptor.getValue();
        assertThat(capturedAccount.getId()).isEqualTo(account.getId());
        assertThat(capturedAccount.getEmail()).isEqualTo(account.getEmail().getValue());
        assertThat(capturedAccount.getPassword()).isEqualTo(account.getPassword().getValue());
        assertThat(capturedAccount.getRole()).isEqualTo(account.getRole().name());
        assertThat(capturedAccount.getEmailIdentityEntity().getEmail()).isEqualTo(account.getEmail().getValue());
        assertThat(capturedAccount.getEmailIdentityEntity().getPassword()).isEqualTo(account.getPassword().getValue());
    }

    @Test
    void getByEmail_whenUserIsFound_shouldReturnAnAccount() {
        when(accountDataSource.findByEmail(ACCOUNT_EMAIL)).thenReturn(Optional.of(ACCOUNT_ENTITY));

        Account result = sut.getByEmail(new AccountEmail(ACCOUNT_EMAIL)).get();

        assertThat(result.getId()).isEqualTo(ACCOUNT_ENTITY.getId());
        assertThat(result.getEmail().getValue()).isEqualTo(ACCOUNT_ENTITY.getEmail());
        assertThat(result.getPassword().getValue()).isEqualTo(ACCOUNT_ENTITY.getPassword());
        assertThat(result.getRole().name()).isEqualTo(ACCOUNT_ENTITY.getRole());
    }

    @Test
    void getByEmail_whenUserIsNotFound_shouldReturnEmptyOptional() {
        when(accountDataSource.findByEmail(ACCOUNT_EMAIL)).thenReturn(Optional.empty());

        Optional<Account> result = sut.getByEmail(new AccountEmail(ACCOUNT_EMAIL));

        assertThat(result).isEmpty();
    }

    @Test
    void getById_whenUserIsFound_shouldReturnAnAccount() {
        when(accountDataSource.findById(ACCOUNT_ENTITY.getId())).thenReturn(Optional.of(ACCOUNT_ENTITY));

        Account got = sut.getById(ACCOUNT_ENTITY.getId()).get();

        assertThat(got.getId()).isEqualTo(ACCOUNT_ENTITY.getId());
        assertThat(got.getEmail().getValue()).isEqualTo(ACCOUNT_ENTITY.getEmail());
        assertThat(got.getPassword().getValue()).isEqualTo(ACCOUNT_ENTITY.getPassword());
        assertThat(got.getRole().name()).isEqualTo(ACCOUNT_ENTITY.getRole());
    }

    @Test
    void getById_whenUserIsNotFound_shouldReturnNothing() {
        when(accountDataSource.findById(any(UUID.class))).thenReturn(Optional.empty());

        Optional<Account> got = sut.getById(UUID.randomUUID());

        assertThat(got).isEmpty();
    }
}