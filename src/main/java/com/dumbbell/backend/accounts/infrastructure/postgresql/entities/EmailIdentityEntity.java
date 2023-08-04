package com.dumbbell.backend.accounts.infrastructure.postgresql.entities;

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
@Table(name = "email_identities", schema = "accounts")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class EmailIdentityEntity {
    @Id
    private UUID id;
    @Column(unique = true)
    private String email;
    private String password;
}

