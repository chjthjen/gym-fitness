package com.example.gymfitness.data.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Upsert;

import com.example.gymfitness.data.entities.WorkoutLog;

import java.util.Date;
import java.util.List;

@Dao
public interface WorkoutLogDAO {
    @Query("SELECT * FROM WorkoutLog WHERE date = :date")
    List<WorkoutLog> getWorkoutLogByDate(Date date);

    @Upsert
    void insertWorkoutLog(WorkoutLog workoutLog);

    //check if the workout is already in the database on the same date
    @Query("SELECT * FROM WorkoutLog WHERE workout_name = :workout_name AND date = :date LIMIT 1")
    WorkoutLog checkWorkout(String workout_name, Date date);

    @Query("SELECT DISTINCT date FROM WorkoutLog")
    List<Date> getAllDates();

}