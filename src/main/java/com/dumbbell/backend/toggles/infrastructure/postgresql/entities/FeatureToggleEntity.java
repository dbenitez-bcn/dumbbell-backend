package com.dumbbell.backend.toggles.infrastructure.postgresql.entities;

import com.dumbbell.backend.core.infrastructure.DumbbellEntity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "toggles", schema = "toggles")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FeatureToggleEntity extends DumbbellEntity {
    @Id
    private String name;
    private boolean value;
}
