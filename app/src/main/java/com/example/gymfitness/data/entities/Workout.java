package com.example.gymfitness.data.entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Workout")
public class Workout implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int workout_id;
    private int kcal;
    private int exerciseCount;
    private String level;
    private String tag;
    private String thumbnail;
    private int totalTime;

    public Workout()
    {

    }

    public Workout(int workout_id, int kcal, int exerciseCount, String level, String tag, String thumbnail, int totalTime) {
        this.workout_id = workout_id;
        this.kcal = kcal;
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

    public int getKcal() {
        return kcal;
    }

    public void setKcal(int kcal) {
        this.kcal = kcal;
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
