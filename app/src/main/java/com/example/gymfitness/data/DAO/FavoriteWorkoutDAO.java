package com.example.gymfitness.data.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.gymfitness.data.entities.FavoriteWorkout;

import java.util.List;

@Dao
public interface FavoriteWorkoutDAO {
    @Query("SELECT * FROM FavoriteWorkout")
    List<FavoriteWorkout> getAll();

    @Insert
    void insert(FavoriteWorkout favoriteWorkout);

    @Query("DELETE FROM FavoriteWorkout WHERE workout_name = :workout_name")
    void delete(String workout_name);
}
