package com.example.gymfitness.data.Entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Workout")
public class Workout implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int workout_id;
    private String workout_name;
    private int workout_calories;
    private String workout_thumbnail;
    private String tag;
    private int is_favorite;
    private int total_time;
    private int level;

    public Workout(int workout_id, String workout_name, int workout_calories, String workout_thumbnail, String tag, int is_favorite, int total_time, int level) {
        this.workout_id = workout_id;
        this.workout_name = workout_name;
        this.workout_calories = workout_calories;
        this.workout_thumbnail = workout_thumbnail;
        this.tag = tag;
        this.is_favorite = is_favorite;
        this.total_time = total_time;
        this.level = level;
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

    public int getWorkout_calories() {
        return workout_calories;
    }

    public void setWorkout_calories(int workout_calories) {
        this.workout_calories = workout_calories;
    }

    public String getWorkout_thumbnail() {
        return workout_thumbnail;
    }

    public void setWorkout_thumbnail(String workout_thumbnail) {
        this.workout_thumbnail = workout_thumbnail;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getIs_favorite() {
        return is_favorite;
    }

    public void setIs_favorite(int is_favorite) {
        this.is_favorite = is_favorite;
    }

    public int getTotal_time() {
        return total_time;
    }

    public void setTotal_time(int total_time) {
        this.total_time = total_time;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
