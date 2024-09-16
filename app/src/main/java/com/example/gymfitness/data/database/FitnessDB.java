package com.example.gymfitness.data.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.gymfitness.data.DAO.FavoriteArticleDAO;
import com.example.gymfitness.data.DAO.FavoriteExerciseDAO;
import com.example.gymfitness.data.DAO.FavoriteWorkoutDAO;
import com.example.gymfitness.data.DAO.RoutineRoundDAO;
import com.example.gymfitness.data.DAO.UserInformationDAO;
import com.example.gymfitness.data.entities.FavoriteArticle;
import com.example.gymfitness.data.entities.FavoriteExercise;
import com.example.gymfitness.data.entities.FavoriteWorkout;
import com.example.gymfitness.data.entities.Exercise;
import com.example.gymfitness.data.entities.RoutineRound;
import com.example.gymfitness.data.entities.Article;
import com.example.gymfitness.data.entities.Challenge;
import com.example.gymfitness.data.entities.Notification;
import com.example.gymfitness.data.entities.ProgessTracking;
import com.example.gymfitness.data.entities.RoutineRoundExerciseCrossRef;
import com.example.gymfitness.data.entities.UserInformation;
import com.example.gymfitness.utils.Converters;

@Database(
        entities =
                {
                        Notification.class,
                        ProgessTracking.class,
                        UserInformation.class,
                        Article.class,
                        Challenge.class,
                        RoutineRound.class,
                        FavoriteExercise.class,
                        FavoriteWorkout.class,
                        Exercise.class,
                        RoutineRoundExerciseCrossRef.class,
                        FavoriteArticle.class
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

    public abstract UserInformationDAO userInformationDAO();
    public abstract RoutineRoundDAO routineRoundDAO();
    public abstract FavoriteWorkoutDAO favoriteWorkoutDAO();
    public abstract FavoriteExerciseDAO favoriteExerciseDAO();
    public abstract FavoriteArticleDAO favoriteArticleDAO();
}
