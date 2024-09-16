package com.example.gymfitness.helpers;

import android.content.Context;

import com.example.gymfitness.data.database.FitnessDB;
import com.example.gymfitness.data.entities.Article;
import com.example.gymfitness.data.entities.Exercise;
import com.example.gymfitness.data.entities.FavoriteArticle;
import com.example.gymfitness.data.entities.FavoriteExercise;
import com.example.gymfitness.data.entities.FavoriteWorkout;
import com.example.gymfitness.data.entities.Workout;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavoriteHelper {
    ExecutorService executorService;
    public void setFavorite(Object object, boolean is_favorite, Context context) {
        executorService = Executors.newSingleThreadExecutor();
        if (object instanceof Workout) {
            Workout workout = (Workout) object;
            if (is_favorite) {
                executorService.execute(() -> {
                    FavoriteWorkout favoriteWorkout = new FavoriteWorkout();
                    favoriteWorkout.setWorkout_name(workout.getWorkout_name());
                    FitnessDB.getInstance(context).favoriteWorkoutDAO().insert(favoriteWorkout);
                });
            } else {
                executorService.execute(() -> {
                    FitnessDB.getInstance(context).favoriteWorkoutDAO().delete(workout.getWorkout_name());
                });
            }
        }
        else if (object instanceof Exercise) {
            Exercise exercise = (Exercise) object;
            if (is_favorite) {
                executorService.execute(() -> {
                    FavoriteExercise favoriteExercise = new FavoriteExercise();
                    favoriteExercise.setExercise_name(exercise.getExercise_name());
                    FitnessDB.getInstance(context).favoriteExerciseDAO().insert(favoriteExercise);
                });
            } else {
                executorService.execute(() -> {
                    FitnessDB.getInstance(context).favoriteExerciseDAO().delete(exercise.getExercise_name());
                });
            }
        }
        else if(object instanceof Article){
            Article article = (Article) object;
            if (is_favorite) {
                executorService.execute(() -> {
                    FavoriteArticle favoriteArticle = new FavoriteArticle();
                    favoriteArticle.setArticle_name(article.getArticle_title());
                    FitnessDB.getInstance(context).favoriteArticleDAO().insert(favoriteArticle);
                });
            } else {
                executorService.execute(() -> {
                    FitnessDB.getInstance(context).favoriteArticleDAO().delete(article.getArticle_title());
                });
            }
        }
    }
}
