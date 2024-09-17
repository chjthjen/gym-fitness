// HomeViewModel.java
package com.example.gymfitness.viewmodels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gymfitness.data.database.FitnessDB;
import com.example.gymfitness.data.entities.Article;
import com.example.gymfitness.data.entities.Workout;
import com.example.gymfitness.firebase.FirebaseRepository;
import com.example.gymfitness.utils.Resource;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeViewModel extends ViewModel {
    private final FirebaseRepository repository;
    private final MutableLiveData<Resource<ArrayList<Workout>>> workoutsLiveData = new MutableLiveData<>();
    private final MutableLiveData<Resource<ArrayList<Article>>> articlesLiveData = new MutableLiveData<>();
    private final MutableLiveData<Resource<ArrayList<Workout>>> roundExerciseLiveData = new MutableLiveData<>();

    public LiveData<Resource<ArrayList<Workout>>> getRoundExercise() {
        return roundExerciseLiveData;
    }

    private FitnessDB fitnessDB;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private String userLevel;

    public HomeViewModel() {
        repository = new FirebaseRepository();
    }

    public LiveData<Resource<ArrayList<Workout>>> getWorkouts() {
        return workoutsLiveData;
    }

    public LiveData<Resource<ArrayList<Article>>> getArticles() {
        return articlesLiveData;
    }

    public void setUserLevel(Context context) {
        fitnessDB = FitnessDB.getInstance(context);
        executorService.execute(() -> {
            userLevel = fitnessDB.userInformationDAO().getUserInformation().getLevel();

            if (userLevel != null) {
                loadWorkoutsByLevel();
            } else {

                workoutsLiveData.setValue(new Resource.Error<>("User level not found"));
            }
        });
    }

    public void loadWorkoutsByLevel() {
        workoutsLiveData.postValue(new Resource.Loading<>());
        repository.getWorkoutsByLevel(userLevel, new FirebaseRepository.WorkoutCallback() {
            @Override
            public void onCallback(List<Workout> workouts) {
                workoutsLiveData.postValue(new Resource.Success<>(new ArrayList<>(workouts)));
            }

            @Override
            public void onError(DatabaseError error) {
                workoutsLiveData.postValue(new Resource.Error<>(error.getMessage()));
            }
        });
    }

    public void loadArticlesItem() {
        articlesLiveData.setValue(new Resource.Loading<>());
        repository.getArticles(new FirebaseRepository.ArticleCallback() {
            @Override
            public void onCallback(List<Article> articles) {
                articlesLiveData.setValue(new Resource.Success<>(new ArrayList<>(articles)));
            }

            @Override
            public void onError(DatabaseError error) {
                articlesLiveData.setValue(new Resource.Error<>(error.getMessage()));
            }
        });
    }



    public void loadRoundExercise() {
        workoutsLiveData.setValue(new Resource.Loading<>());
        repository.getRoundExercise(new FirebaseRepository.WorkoutCallback() {
            @Override
            public void onCallback(List<Workout> workouts) {
                workoutsLiveData.setValue(new Resource.Success<>(new ArrayList<>(workouts)));
            }

            @Override
            public void onError(DatabaseError error) {
                workoutsLiveData.setValue(new Resource.Error<>(error.getMessage()));
            }
        });
    }
}