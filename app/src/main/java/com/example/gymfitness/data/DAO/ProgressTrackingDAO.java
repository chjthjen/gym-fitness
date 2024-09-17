package com.example.gymfitness.data.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


import com.example.gymfitness.data.entities.ProgressTracking;

import java.util.List;

@Dao
public interface ProgressTrackingDAO {
    @Query("SELECT * FROM ProgressTracking")
    List<ProgressTracking> getAll();

    @Insert
    void insert(ProgressTracking progressTracking);
}
