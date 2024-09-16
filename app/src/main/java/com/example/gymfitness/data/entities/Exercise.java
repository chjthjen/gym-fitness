package com.example.gymfitness.data.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Exercises")
public class Exercise implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int exercise_id;
    private String exercise_name;
    private int duration;
    private String exerciseThumb;
    private String level;
    private String link;
    private int rep;

    public Exercise(){}

    public Exercise(int exercise_id, String exercise_name, int duration, String exerciseThumb, String level, String link, int rep) {
        this.exercise_id = exercise_id;
        this.exercise_name = exercise_name;
        this.duration = duration;
        this.exerciseThumb = exerciseThumb;
        this.level = level;
        this.link = link;
        this.rep = rep;
    }

    public int getExercise_id() {
        return exercise_id;
    }

    public void setExercise_id(int exercise_id) {
        this.exercise_id = exercise_id;
    }

    public String getExercise_name() {
        return exercise_name;
    }

    public void setExercise_name(String exercise_name) {
        this.exercise_name = exercise_name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getExerciseThumb() {
        return exerciseThumb;
    }

    public void setExerciseThumb(String exerciseThumb) {
        this.exerciseThumb = exerciseThumb;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getRep() {
        return rep;
    }

    public void setRep(int rep) {
        this.rep = rep;
    }
}
