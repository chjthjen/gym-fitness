package com.example.gymfitness.data.Entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Challenge",
        foreignKeys = @ForeignKey(entity = Workout.class,
                parentColumns = "workout_id",
                childColumns = "workout_id"))
public class Challenge implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int workout_id;

    public Challenge(int id, String name, int workout_id) {
        this.id = id;
        this.name = name;
        this.workout_id = workout_id;
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

    public int getWorkout_id() {
        return workout_id;
    }

    public void setWorkout_id(int workout_id) {
        this.workout_id = workout_id;
    }
}
