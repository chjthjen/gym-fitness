package com.example.gymfitness.data.entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Workout")
public class Workout implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int workout_id;
    private String workout_name;
    private int Kcal;
    private int exerciseCount;
    private String level;
    private String tag;
    private String thumbnail;
    private int totalTime;

    public Workout() {

    }

    public Workout(int workout_id, String workout_name, int kcal, int exerciseCount, String level, String tag, String thumbnail, int totalTime) {
        this.workout_id = workout_id;
        this.workout_name = workout_name;
        Kcal = kcal;
        this.exerciseCount = exerciseCount;
        this.level = level;
        this.tag = tag;
        this.thumbnail = thumbnail;
        this.totalTime = totalTime;
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

    public int getKcal() {
        return Kcal;
    }

    public void setKcal(int kcal) {
        Kcal = kcal;
    }

    public int getExerciseCount() {
        return exerciseCount;
    }

    public void setExerciseCount(int exerciseCount) {
        this.exerciseCount = exerciseCount;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }
}