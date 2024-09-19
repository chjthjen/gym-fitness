package com.example.gymfitness.data.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


import com.example.gymfitness.data.DayOverview;
import com.example.gymfitness.data.entities.ProgressTracking;

import java.util.List;

@Dao
public interface ProgressTrackingDAO {
    @Query("SELECT * FROM ProgressTracking")
    List<ProgressTracking> getAll();

    @Insert
    void insert(ProgressTracking progressTracking);

    @Query("SELECT DATE(datetime_tracking) as date, SUM(duration) as duration, SUM(rep) as rep FROM ProgressTracking GROUP BY DATE(datetime_tracking)")
    List<DayOverview> getDayOverview();

    @Query("SELECT strftime('%Y-%m', datetime_tracking) as date, SUM(duration) as duration, SUM(rep) as rep FROM ProgressTracking GROUP BY strftime('%Y-%m', datetime_tracking)")
    List<DayOverview> getMonthOverview();
}
