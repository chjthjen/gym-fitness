package com.example.gymfitness.data.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "WorkoutLog")
public class WorkoutLog implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int workout_id;
    private String workout_name;
    private int kcal;
    private Date date;
    private int totalTime;

    public WorkoutLog() {
    }

    @Ignore
    public WorkoutLog(int workout_id, String workout_name, int kcal, Date date, int totalTime) {
        this.workout_id = workout_id;
        this.workout_name = workout_name;
        this.kcal = kcal;
        this.date = date;
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
        return kcal;
    }

    public void setKcal(int kcal) {
        this.kcal = kcal;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }
}
