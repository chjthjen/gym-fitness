package com.example.gymfitness.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.gymfitness.helpers.ProgressTrackHelper;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class ProgressTracking implements Serializable {
    @PrimaryKey(autoGenerate = true)
    int pro_id;
    @TypeConverters(ProgressTrackHelper.class)
    Date datetime_tracking;
    String exercise_id;
    int rep;
    int duration;
    public ProgressTracking() {
    }

    public ProgressTracking(int pro_id, Date datetime_tracking, String exercise_id) {
        this.pro_id = pro_id;
        this.datetime_tracking = datetime_tracking;
        this.exercise_id = exercise_id;
    }

    public int getPro_id() {
        return pro_id;
    }

    public void setPro_id(int pro_id) {
        this.pro_id = pro_id;
    }

    public Date getDatetime_tracking() {
        return datetime_tracking;
    }

    public void setDatetime_tracking(Date datetime_tracking) {
        this.datetime_tracking = datetime_tracking;
    }

    public String getExercise_id() {
        return exercise_id;
    }

    public void setExercise_id(String exercise_id) {
        this.exercise_id = exercise_id;
    }

    public int getRep() {
        return rep;
    }

    public void setRep(int rep) {
        this.rep = rep;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
