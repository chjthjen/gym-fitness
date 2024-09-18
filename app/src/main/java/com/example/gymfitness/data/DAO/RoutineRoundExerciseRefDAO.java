package com.example.gymfitness.data.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;

import com.example.gymfitness.data.entities.Exercise;

@Dao
public interface RoutineRoundExerciseRefDAO {
    @Query("DELETE FROM RoutineRoundExerciseCrossRef WHERE routineRoundId = :roundId AND exerciseId = :exerciseId")
    void deleteRoutineRoundExerciseCrossRef(int roundId, int exerciseId);
}