package com.example.gymfitness.viewmodels;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gymfitness.data.database.FitnessDB;
import com.example.gymfitness.data.entities.Article;
import com.example.gymfitness.data.entities.Workout;
import com.example.gymfitness.utils.Resource;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeViewModel extends ViewModel {
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Workout");
    private DatabaseReference databaseArticles = FirebaseDatabase.getInstance().getReference("Articles");
    private MutableLiveData<Resource<ArrayList<Workout>>> workoutsLiveData = new MutableLiveData<>();
    public LiveData<Resource<ArrayList<Workout>>> getWorkouts() {
        return workoutsLiveData;
    }

    private MutableLiveData<Resource<ArrayList<Article>>> articlesLiveData = new MutableLiveData<>();
    public LiveData<Resource<ArrayList<Article>>> getArticles() {
        return articlesLiveData;
    }
    private FitnessDB fitnessDB;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private String userLevel;
    public void setUserLevel(Context context)
    {
        fitnessDB = FitnessDB.getInstance(context);
        executorService.execute(() -> {
            userLevel = fitnessDB.userInformationDAO().getUserInformation().getLevel();
        });
    }

    public void loadWorkoutsByLevel() {
        workoutsLiveData.setValue(new Resource.Loading<>());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Workout> workoutList = new ArrayList<>();
                for (DataSnapshot workoutSnapshot : dataSnapshot.getChildren()) {
                    Workout workout = workoutSnapshot.getValue(Workout.class);
                    workout.setWorkout_name(workoutSnapshot.getKey());
                    if (Objects.equals(workout.getLevel(), userLevel)) {
                        workoutList.add(workout);
                    }
                }
                workoutsLiveData.setValue(new Resource.Success<>(workoutList));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                workoutsLiveData.setValue(new Resource.Error<>(databaseError.getMessage()));
            }
        });
    }
    public void loadArticles() {
        articlesLiveData.setValue(new Resource.Loading<>());
        databaseArticles.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Article> articleList = new ArrayList<>();
                for (DataSnapshot articleSnapshot : dataSnapshot.getChildren()) {
                    Article article = articleSnapshot.getValue(Article.class);
                    String articleTitle = articleSnapshot.getKey();
                    article.setArticle_title(articleTitle);
                    String thumbnailUrl = articleSnapshot.child("thumbnail").getValue(String.class);
                    article.setArticle_thumbnail(thumbnailUrl);

                    Log.d("Article Thumbnail", thumbnailUrl);
                    articleList.add(article);
                }
                articlesLiveData.setValue(new Resource.Success<>(articleList));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                articlesLiveData.setValue(new Resource.Error<>(databaseError.getMessage()));
            }
        });
    }

}
