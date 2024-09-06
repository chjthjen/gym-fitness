package com.example.gymfitness.data.Entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Workout")
public class Workout implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int workout_id;

    private String workout_name;

    private double workout_calories;

    private String workout_thumbnail;

    private int level_id;

    public Workout(int workout_id, String workout_name, double workout_calories, String workout_thumbnail, int level_id) {
        this.workout_id = workout_id;
        this.workout_name = workout_name;
        this.workout_calories = workout_calories;
        this.workout_thumbnail = workout_thumbnail;
        this.level_id = level_id;
    }

    public int getWorkout_id() {
        return workout_id;
    }

    public void setWorkout_id(int workout_id) {
        this.workout_id = workout_id;
    }

    public String getWorkout_name() {
        return workout_name;
    }

    public void setWorkout_name(String workout_name) {
        this.workout_name = workout_name;
    }

    public double getWorkout_calories() {
        return workout_calories;
    }

    public void setWorkout_calories(double workout_calories) {
        this.workout_calories = workout_calories;
    }

    public String getWorkout_thumbnail() {
        return workout_thumbnail;
    }

    public void setWorkout_thumbnail(String workout_thumbnail) {
        this.workout_thumbnail = workout_thumbnail;
    }

    public int getLevel_id() {
        return level_id;
    }

    public void setLevel_id(int level_id) {
        this.level_id = level_id;
    }
}
