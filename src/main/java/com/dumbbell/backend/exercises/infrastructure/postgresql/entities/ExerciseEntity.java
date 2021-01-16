package com.dumbbell.backend.exercises.infrastructure.postgresql.entities;

import com.dumbbell.backend.core.infrastructure.DumbbellEntity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "exercises", schema = "exercises")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ExerciseEntity extends DumbbellEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private Integer difficulty;
}
