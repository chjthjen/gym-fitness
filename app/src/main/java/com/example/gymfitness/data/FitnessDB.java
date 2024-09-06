package com.example.gymfitness.data;

import android.content.Context;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//@Database(
//        entities = {UserInformation.class},
//        version = 1,
//        autoMigrations = {
//                @AutoMigration(from = 1, to = 2)
//        }
//)
public abstract class FitnessDB extends RoomDatabase {
    private static final String DATABASE_NAME = "gym_fitness.db";
    private static FitnessDB instance;

    public static synchronized FitnessDB getInstance(Context context)
    {
        if(instance == null)
        {
             instance = Room.databaseBuilder(context.getApplicationContext(),FitnessDB.class,DATABASE_NAME)
                     .allowMainThreadQueries()
                     .build();
        }
        return instance;
    }


}
