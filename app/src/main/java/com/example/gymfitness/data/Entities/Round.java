package com.example.gymfitness.data.Entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Round",
        foreignKeys = @ForeignKey(entity = Workout.class,
                parentColumns = "workout_id",
                childColumns = "workout_id"))
public class Round implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int round_id;
    private String round_name;
    private int workout_id;

    public Round(int round_id, String round_name, int workout_id) {
        this.round_id = round_id;
        this.round_name = round_name;
        this.workout_id = workout_id;
    }

    public int getRound_id() {
        return round_id;
    }

    public void setRound_id(int round_id) {
        this.round_id = round_id;
    }

    public String getRound_name() {
        return round_name;
    }

    public void setRound_name(String round_name) {
        this.round_name = round_name;
    }

    public int getWorkout_id() {
        return workout_id;
    }

    public void setWorkout_id(int workout_id) {
        this.workout_id = workout_id;
    }
}
