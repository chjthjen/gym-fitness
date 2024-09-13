package com.example.gymfitness.data.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity(tableName = "ProgressTracking",
        foreignKeys = @ForeignKey(entity = Exercise.class,
                parentColumns = "exercise_id",
                childColumns = "exercise_id"))
public class ProgessTracking implements Serializable {
    @PrimaryKey(autoGenerate = true)
    int pro_id;
    LocalDateTime datetime_tracking;
    String exercise_id;

    public ProgessTracking(int pro_id, LocalDateTime datetime_tracking, String exercise_id) {
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

    public LocalDateTime getDatetime_tracking() {
        return datetime_tracking;
    }

    public void setDatetime_tracking(LocalDateTime datetime_tracking) {
        this.datetime_tracking = datetime_tracking;
    }

    public String getExercise_id() {
        return exercise_id;
    }

    public void setExercise_id(String exercise_id) {
        this.exercise_id = exercise_id;
    }
}
