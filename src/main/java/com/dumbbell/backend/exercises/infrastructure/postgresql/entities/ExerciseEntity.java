package com.dumbbell.backend.exercises.infrastructure.postgresql.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "exercises", schema = "exercises")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ExerciseEntity {
    @Id
    private Integer id;
    private String name;
    private String description;
    private Integer difficulty;
}
