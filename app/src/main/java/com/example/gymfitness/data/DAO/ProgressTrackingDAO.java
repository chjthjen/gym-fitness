package com.example.gymfitness.data.DAO;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.gymfitness.viewmodels.ProgressTracking;

import java.util.List;

@Dao
public interface ProgressTrackingDAO {
    @Query("SELECT * FROM ProgressTracking")
    List<ProgressTracking> getAll();
}
