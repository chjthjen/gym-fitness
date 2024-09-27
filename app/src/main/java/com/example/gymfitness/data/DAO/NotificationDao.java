package com.example.gymfitness.data.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.gymfitness.data.entities.Notification;

import java.util.List;

@Dao
public interface NotificationDao {

    @Query("SELECT * FROM Notification ORDER BY date DESC")
    List<Notification> getAllNotifications();

    @Insert
    void insertNotification(Notification notification);

    @Delete
    void delete(Notification notification);
}
