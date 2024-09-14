package com.example.gymfitness.data.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gymfitness.data.entities.RoutineRound;

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
}
