package com.example.gymfitness.data.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "challenge")
public class Challenge implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String name;
    private Long workoutId;

    public Challenge() {
    }

    public Challenge(Long id, String name, Long workoutId) {
        this.id = id;
        this.name = name;
        this.workoutId = workoutId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(Long workoutId) {
        this.workoutId = workoutId;
    }
}
