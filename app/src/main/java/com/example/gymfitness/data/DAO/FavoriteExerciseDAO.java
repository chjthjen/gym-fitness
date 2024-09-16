package com.example.gymfitness.data.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.gymfitness.data.entities.FavoriteExercise;

import java.util.List;
@Dao
public interface FavoriteExerciseDAO {
    @Query("SELECT * FROM FavoriteExercise")
    List<FavoriteExercise> getAll();

    @Insert
    void insert(FavoriteExercise favoriteExercise);

    @Query("DELETE FROM FavoriteExercise WHERE exercise_name = :exercise_name")
    void delete(String exercise_name);

    @Query("SELECT * FROM FavoriteExercise WHERE exercise_name = :exercise_name")
    FavoriteExercise getExercise(String exercise_name);
}
