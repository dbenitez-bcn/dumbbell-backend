package com.dumbbell.backend.accounts.infrastructure.postgresql.entities;

import com.dumbbell.backend.core.infrastructure.DumbbellEntity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "accounts", schema = "accounts")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AccountEntity extends DumbbellEntity {
    @Id
    private UUID id;
    @Column(unique = true)
    private String email;
    private String password;
    private String role;
}
