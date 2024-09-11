package com.example.gymfitness.data.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


import com.example.gymfitness.data.Entities.UserInformation;

import java.util.List;
@Dao
public interface UserInformationDAO{

    @Query("SELECT * FROM UserInformation")
    List<UserInformation> getAllWorkouts();

    @Insert
    void insertWorkout(UserInformation userInformation);
}
