package com.example.gymfitness.data.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.gymfitness.data.DAO.UserInformationDAO;
import com.example.gymfitness.data.DAO.WorkoutDAO;
import com.example.gymfitness.data.entities.Article;
import com.example.gymfitness.data.entities.Challenge;
import com.example.gymfitness.data.entities.Exercise;
import com.example.gymfitness.data.entities.Notification;
import com.example.gymfitness.data.entities.ProgessTracking;
import com.example.gymfitness.data.entities.Round;
import com.example.gymfitness.data.entities.UserInformation;
import com.example.gymfitness.data.entities.Workout;
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
        version = 1,
        exportSchema = false
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
    public abstract UserInformationDAO userInformationDAO();


}
