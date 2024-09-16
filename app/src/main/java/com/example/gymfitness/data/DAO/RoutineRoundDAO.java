package com.example.gymfitness.data.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.gymfitness.data.entities.Exercise;
import com.example.gymfitness.data.entities.RoutineRound;
import com.example.gymfitness.data.entities.RoutineRoundExerciseCrossRef;

import java.util.List;

@Dao
public interface RoutineRoundDAO {
    @Insert
    void insert(RoutineRound routineRound);

    @Delete
    void delete(RoutineRound routineRound);

    @Query("SELECT * FROM RoutineRounds")
    LiveData<List<RoutineRound>> getAllRoutineRounds();

    @Query("SELECT * FROM RoutineRounds")
    List<RoutineRound> getAllRoutineRoundsDirect();

    @Update
    void update(RoutineRound routineRound);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertExercise(Exercise exercise);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRoutineRoundExerciseCrossRef(RoutineRoundExerciseCrossRef crossRef);

    @Query("SELECT * FROM Exercises WHERE exercise_name = :exerciseName LIMIT 1")
    Exercise getExerciseByName(String exerciseName);

    @Query("SELECT * FROM RoutineRounds WHERE id = :roundId LIMIT 1")
    RoutineRound getRoutineRoundById(int roundId);

    @Transaction
    @Query("SELECT * FROM Exercises INNER JOIN RoutineRoundExerciseCrossRef ON Exercises.exercise_id = RoutineRoundExerciseCrossRef.exerciseId WHERE RoutineRoundExerciseCrossRef.routineRoundId = :roundId")
    LiveData<List<Exercise>> getExercisesForRoutineRound(int roundId);

}
