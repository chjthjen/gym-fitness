package com.example.gymfitness.data.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Upsert;


import com.example.gymfitness.data.Entities.UserInformation;

import java.util.List;
@Dao
public interface UserInformationDAO{

    @Query("SELECT * FROM UserInformation")
    UserInformation getUserInformation();

    @Upsert
    void upsertWorkout(UserInformation userInformation);

}
