package com.example.gymfitness.data.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "progress_tracking")
public class ProgessTracking implements Serializable {
    @PrimaryKey(autoGenerate = true)
    int pro_id;
    String datetime_tracking;
    String workout_id;

    public ProgessTracking(int pro_id, String datetime_tracking, String workout_id) {
        this.pro_id = pro_id;
        this.datetime_tracking = datetime_tracking;
        this.workout_id = workout_id;
    }

    public ProgessTracking() {
    }

    public int getPro_id() {
        return pro_id;
    }

    public void setPro_id(int pro_id) {
        this.pro_id = pro_id;
    }

    public String getDatetime_tracking() {
        return datetime_tracking;
    }

    public void setDatetime_tracking(String datetime_tracking) {
        this.datetime_tracking = datetime_tracking;
    }

    public String getWorkout_id() {
        return workout_id;
    }

    public void setWorkout_id(String workout_id) {
        this.workout_id = workout_id;
    }
}
