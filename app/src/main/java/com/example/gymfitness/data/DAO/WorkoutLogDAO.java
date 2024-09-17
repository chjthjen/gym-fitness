package com.example.gymfitness.data.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.gymfitness.data.entities.WorkoutLog;

@Dao
public interface WorkoutLogDAO {
    @Query("SELECT * FROM WorkoutLog WHERE date = :date")
    WorkoutLog getWorkoutLogByDate(String date);

    @Insert
    void insertWorkoutLog(WorkoutLog workoutLog);
}