package com.example.gymfitness.helpers;

import android.content.Context;
import android.widget.ImageView;

import com.example.gymfitness.R;
import com.example.gymfitness.data.database.FitnessDB;
import com.example.gymfitness.data.entities.Article;
import com.example.gymfitness.data.entities.Exercise;
import com.example.gymfitness.data.entities.FavoriteArticle;
import com.example.gymfitness.data.entities.FavoriteExercise;
import com.example.gymfitness.data.entities.FavoriteWorkout;
import com.example.gymfitness.data.entities.Workout;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class FavoriteHelper {
    private static FavoriteHelper instance;

    public static FavoriteHelper getInstance() {
        if (instance == null) {
            instance = new FavoriteHelper();
        }
        return instance;
    }

    static ExecutorService executorService;

    public static void setFavorite(Object object, Context context, ImageView star) {
        AtomicBoolean is_favorite = new AtomicBoolean(false);
        executorService = Executors.newSingleThreadExecutor();
        if (object instanceof Workout) {
            Workout workout = (Workout) object;
            executorService.execute(() -> {
                FavoriteWorkout favWorkout = FitnessDB.getInstance(context).favoriteWorkoutDAO().getWorkout(workout.getWorkout_name());
                if (favWorkout != null) {
                    is_favorite.set(true);
                }
                if (!is_favorite.get()) {
                    executorService.execute(() -> {
                        FavoriteWorkout favoriteWorkout = new FavoriteWorkout();
                        favoriteWorkout.setWorkout_name(workout.getWorkout_name());
                        FitnessDB.getInstance(context).favoriteWorkoutDAO().insert(favoriteWorkout);
                        // change icon
                        star.setImageResource(R.drawable.start_small_on);
                    });
                } else {
                    executorService.execute(() -> {
                        FitnessDB.getInstance(context).favoriteWorkoutDAO().delete(workout.getWorkout_name());
                        star.setImageResource(R.drawable.start_small_off);
                    });
                }
            });
        } else if (object instanceof Exercise) {
            Exercise exercise = (Exercise) object;
            executorService.execute(() -> {
                FavoriteExercise favExercise = FitnessDB.getInstance(context).favoriteExerciseDAO().getExercise(exercise.getExercise_name());
                if (favExercise != null) {
                    is_favorite.set(true);
                }
                if (!is_favorite.get()) {
                    executorService.execute(() -> {
                        FavoriteExercise favoriteExercise = new FavoriteExercise();
                        favoriteExercise.setExercise_name(exercise.getExercise_name());
                        FitnessDB.getInstance(context).favoriteExerciseDAO().insert(favoriteExercise);
                        star.setImageResource(R.drawable.start_small_on);
                    });
                } else {
                    executorService.execute(() -> {
                        FitnessDB.getInstance(context).favoriteExerciseDAO().delete(exercise.getExercise_name());
                        star.setImageResource(R.drawable.start_small_off);
                    });
                }
            });
        } else if (object instanceof Article) {
            Article article = (Article) object;
            executorService.execute(() -> {
                FavoriteArticle favArticle = FitnessDB.getInstance(context).favoriteArticleDAO().getArticle(article.getArticle_title());
                if (favArticle != null) {
                    is_favorite.set(true);
                }
                if (!is_favorite.get()) {
                    executorService.execute(() -> {
                        FavoriteArticle favoriteArticle = new FavoriteArticle();
                        favoriteArticle.setArticle_name(article.getArticle_title());
                        FitnessDB.getInstance(context).favoriteArticleDAO().insert(favoriteArticle);
                        star.setImageResource(R.drawable.start_small_on);

                    });
                } else {
                    executorService.execute(() -> {
                        FitnessDB.getInstance(context).favoriteArticleDAO().delete(article.getArticle_title());
                        star.setImageResource(R.drawable.start_small_off);
                    });
                }
            });
        }
    }

    public static boolean checkFavorite(Object object, Context context, ImageView star) {
        AtomicBoolean is_favorite = new AtomicBoolean(false);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        if (object instanceof Workout) {
            Workout workout = (Workout) object;
            executorService.execute(() -> {
                FavoriteWorkout favWorkout = FitnessDB.getInstance(context).favoriteWorkoutDAO().getWorkout(workout.getWorkout_name());
                star.post(() -> {
                    if (favWorkout != null) {
                        is_favorite.set(true);
                        star.setImageResource(R.drawable.start_small_on);
                    } else {
                        star.setImageResource(R.drawable.start_small_off);
                    }
                });
            });
        } else if (object instanceof Exercise) {
            Exercise exercise = (Exercise) object;
            executorService.execute(() -> {
                FavoriteExercise favExercise = FitnessDB.getInstance(context).favoriteExerciseDAO().getExercise(exercise.getExercise_name());
                star.post(() -> {
                    if (favExercise != null) {
                        is_favorite.set(true);
                        star.setImageResource(R.drawable.start_small_on);
                    } else {
                        star.setImageResource(R.drawable.start_small_off);
                    }
                });
            });
        } else if (object instanceof Article) {
            Article article = (Article) object;
            executorService.execute(() -> {
                FavoriteArticle favArticle = FitnessDB.getInstance(context).favoriteArticleDAO().getArticle(article.getArticle_title());
                star.post(() -> {
                    if (favArticle != null) {
                        is_favorite.set(true);
                        star.setImageResource(R.drawable.start_small_on);
                    } else {
                        star.setImageResource(R.drawable.start_small_off);
                    }
                });
            });
        }
        return is_favorite.get();
    }

    public static List<FavoriteWorkout> getListFavoriteWorkout(Context context) {
        AtomicReference<List<FavoriteWorkout>> favoriteWorkouts = new AtomicReference<>();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            favoriteWorkouts.set(FitnessDB.getInstance(context).favoriteWorkoutDAO().getAll());
        });
        return favoriteWorkouts.get();
    }
    public static List<FavoriteArticle> getListFavoriteArticle(Context context) {
        AtomicReference<List<FavoriteArticle>> favoriteArticles = new AtomicReference<>();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            favoriteArticles.set(FitnessDB.getInstance(context).favoriteArticleDAO().getAll());
        });
        return favoriteArticles.get();
    }

}
