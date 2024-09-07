package com.example.gymfitness.data.database;

import android.content.Context;
import android.util.Log;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.gymfitness.data.DAO.WorkoutDAO;
import com.example.gymfitness.data.Entities.Article;
import com.example.gymfitness.data.Entities.Challenge;
import com.example.gymfitness.data.Entities.Exercise;
import com.example.gymfitness.data.Entities.Notification;
import com.example.gymfitness.data.Entities.ProgessTracking;
import com.example.gymfitness.data.Entities.Round;
import com.example.gymfitness.data.Entities.UserInformation;
import com.example.gymfitness.data.Entities.Workout;
import com.example.gymfitness.utils.Converters;

@Database(
        entities =
                {
                        Workout.class,
                        Round.class,
                        Exercise.class,
                        Notification.class,
                        ProgessTracking.class,
                        UserInformation.class,
                        Article.class,
                        Challenge.class
                },
        version = 1
)
@TypeConverters({Converters.class})
public abstract class FitnessDB extends RoomDatabase {
    private static final String DATABASE_NAME = "gym_fitness.db";
    private static FitnessDB instance;

    public static synchronized FitnessDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), FitnessDB.class, DATABASE_NAME)
                    .build();
            Log.d("Database", "Database has been created");

        }
        Log.d("Database", "Database already exists");
        return instance;
    }

    public abstract WorkoutDAO workoutDAO();


}
