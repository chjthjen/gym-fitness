package com.example.gymfitness.data.DAO;

import androidx.room.Query;

import com.example.gymfitness.data.entities.FavoriteExercise;

import java.util.List;

public interface FavoriteExerciseDAO {
    @Query("SELECT * FROM FavoriteExercise")
    abstract List<FavoriteExercise> getAll();
}
