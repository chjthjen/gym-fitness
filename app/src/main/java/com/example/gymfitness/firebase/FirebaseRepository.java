package com.example.gymfitness.firebase;

import androidx.annotation.NonNull;

import com.example.gymfitness.data.entities.Article;
import com.example.gymfitness.data.entities.ArticleDetail;
import com.example.gymfitness.data.entities.Exercise;
import com.example.gymfitness.data.entities.Round;
import com.example.gymfitness.data.entities.Workout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseRepository {

    private final DatabaseReference workoutReference;
    private final DatabaseReference articleReference;

    public FirebaseRepository() {
        workoutReference = FirebaseDatabase.getInstance().getReference("Workout");
        articleReference = FirebaseDatabase.getInstance().getReference("Articles");
    }

    public interface WorkoutCallback {
        void onCallback(List<Workout> workouts);
        void onError(DatabaseError error);
    }

    public interface ArticleCallback {
        void onCallback(List<Article> articles);
        void onError(DatabaseError error);
    }

    public interface ArticleDetailsCallback {
        void onCallback(String title, String publishDate, String description, String thumbnail, List<ArticleDetail> articleDetails);
        void onError(DatabaseError error);
    }
    public void getAllWorkouts(WorkoutCallback callback) {
        workoutReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Workout> workoutList = new ArrayList<>();
                for (DataSnapshot workoutSnapshot : dataSnapshot.getChildren()) {
                    Workout workout = workoutSnapshot.getValue(Workout.class);
                    workout.setWorkout_name(workoutSnapshot.getKey());
                    List<Round> roundsList = new ArrayList<>();
                    for (DataSnapshot roundSnapshot : workoutSnapshot.child("round").getChildren()) {
                        Round round = roundSnapshot.getValue(Round.class);
                        round.setRound_name(roundSnapshot.getKey());
                        List<Exercise> exercisesList = new ArrayList<>();
                        for (DataSnapshot exerciseSnapshot : roundSnapshot.getChildren()) {
                            Exercise exercise = exerciseSnapshot.getValue(Exercise.class);
                            exercise.setExercise_name(exerciseSnapshot.getKey());
                            exercisesList.add(exercise);
                        }
                        round.setExercises(new ArrayList<>(exercisesList));
                        roundsList.add(round);
                    }
                    workout.setRound(new ArrayList<>(roundsList));
                    workoutList.add(workout);
                }
                callback.onCallback(workoutList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError(databaseError);
            }
        });
    }

    public void getWorkoutsByLevel(String userLevel, WorkoutCallback callback) {
        workoutReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Workout> workoutList = new ArrayList<>();
                for (DataSnapshot workoutSnapshot : dataSnapshot.getChildren()) {
                    Workout workout = workoutSnapshot.getValue(Workout.class);
                    workout.setWorkout_name(workoutSnapshot.getKey());
                    if (workout.getLevel() != null && workout.getLevel().equals(userLevel)) {
                        List<Round> roundsList = new ArrayList<>();
                        for (DataSnapshot roundSnapshot : workoutSnapshot.child("round").getChildren()) {
                            Round round = roundSnapshot.getValue(Round.class);
                            round.setRound_name(roundSnapshot.getKey());
                            List<Exercise> exercisesList = new ArrayList<>();
                            for (DataSnapshot exerciseSnapshot : roundSnapshot.getChildren()) {
                                Exercise exercise = exerciseSnapshot.getValue(Exercise.class);
                                exercise.setExercise_name(exerciseSnapshot.getKey());
                                exercisesList.add(exercise);
                            }
                            round.setExercises(new ArrayList<>(exercisesList));
                            roundsList.add(round);
                        }
                        workout.setRound(new ArrayList<>(roundsList));
                        workoutList.add(workout);
                    }
                }
                callback.onCallback(workoutList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError(databaseError);
            }
        });
    }

    public void getArticles(ArticleCallback callback) {
        articleReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Article> articleList = new ArrayList<>();
                for (DataSnapshot articleSnapshot : dataSnapshot.getChildren()) {
                    Article article = articleSnapshot.getValue(Article.class);
                    article.setArticle_title(articleSnapshot.getKey());
                    article.setArticle_thumbnail(articleSnapshot.child("thumbnail").getValue(String.class));
                    articleList.add(article);
                }
                callback.onCallback(articleList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError(databaseError);
            }
        });
    }

    public void getArticleDetails(String articleTitle, ArticleDetailsCallback callback) {
        DatabaseReference articleRef = articleReference.child(articleTitle);
        articleRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String title = snapshot.getKey();
                    String publishDate = snapshot.child("publish_date").getValue(String.class);
                    String description = snapshot.child("description").getValue(String.class);
                    String thumbnail = snapshot.child("thumbnail").getValue(String.class);

                    List<ArticleDetail> details = new ArrayList<>();
                    DataSnapshot contentSnapshot = snapshot.child("content");
                    for (DataSnapshot content : contentSnapshot.getChildren()) {
                        String header = content.getValue(String.class);
                        String contentText = content.getKey();
                        details.add(new ArticleDetail(header, contentText));
                    }
                    callback.onCallback(title, publishDate, description, thumbnail, details);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(error);
            }
        });
    }

    public void getRoundExercise(WorkoutCallback callback) {
        workoutReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Workout> workoutList = new ArrayList<>();
                for (DataSnapshot workoutSnapshot : dataSnapshot.getChildren()) {
                    Workout workout = workoutSnapshot.getValue(Workout.class);
                    workout.setWorkout_name(workoutSnapshot.getKey());
                    List<Round> roundsList = new ArrayList<>();
                    for (DataSnapshot roundSnapshot : workoutSnapshot.child("round").getChildren()) {
                        Round round = roundSnapshot.getValue(Round.class);
                        round.setRound_name(roundSnapshot.getKey());
                        List<Exercise> exercisesList = new ArrayList<>();
                        for (DataSnapshot exerciseSnapshot : roundSnapshot.getChildren()) {
                            Exercise exercise = exerciseSnapshot.getValue(Exercise.class);
                            exercise.setExercise_name(exerciseSnapshot.getKey());
                            exercisesList.add(exercise);
                        }
                        round.setExercises(new ArrayList<>(exercisesList));
                        roundsList.add(round);
                    }
                    workout.setRound(new ArrayList<>(roundsList));
                    workoutList.add(workout);
                }
                callback.onCallback(workoutList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError(databaseError);
            }
        });
    }
}