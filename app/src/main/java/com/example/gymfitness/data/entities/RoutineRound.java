package com.example.gymfitness.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "RoutineRounds")
public class RoutineRound implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;

    public RoutineRound() {}

    public RoutineRound(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public RoutineRound(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}