package com.dumbbell.backend.accounts.infrastructure.postgresql.entities;

import com.dumbbell.backend.core.infrastructure.DumbbellEntity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "accounts", schema = "accounts")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AccountEntity extends DumbbellEntity {
    @Id
    private UUID id;
    @Column(unique = true)
    private String email;
    private String role;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "email_identity_id", referencedColumnName = "id")
    private EmailIdentityEntity emailIdentityEntity;
}
