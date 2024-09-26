package com.example.gymfitness.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gymfitness.data.entities.Article;
import com.example.gymfitness.data.entities.Exercise;
import com.example.gymfitness.data.entities.UserInformation;
import com.example.gymfitness.data.entities.Workout;

import java.util.List;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<Workout> selected = new MutableLiveData<Workout>();
    private final MutableLiveData<List<Workout>> workoutsList = new MutableLiveData<>();


    public void select(Workout item) {
        selected.setValue(item);
    }

    public LiveData<Workout> getSelected() {
        return selected;
    }

    public final MutableLiveData<Exercise> exerciseSelected = new MutableLiveData<Exercise>();

    public void selectExercise(Exercise exercise)
    {
        exerciseSelected.setValue(exercise);
    }

    public LiveData<Exercise> getExerciseSelected()
    {
        return this.exerciseSelected;
    }

    private final MutableLiveData<String> selectedArticleTitle = new MutableLiveData<>();

    public void setSelectedArticle(String articleTitle) {
        selectedArticleTitle.setValue(articleTitle);
    }

    public LiveData<String> getSelectedArticle() {
        return selectedArticleTitle;
    }

    public void setWorkouts(List<Workout> workouts) {
        workoutsList.setValue(workouts);
    }

    // Lấy danh sách Workout
    public LiveData<List<Workout>> getWorkouts() {
        return workoutsList;
    }

    public final MutableLiveData<Article> article = new MutableLiveData<Article>();

    public void setArticle(Article article)
    {
        this.article.setValue(article);
    }

    public LiveData<Article> getArticle()
    {
        return this.article;
    }

    public MutableLiveData<UserInformation> username = new MutableLiveData<>();

    public void setUsername(UserInformation username)
    {
        this.username.setValue(username);
    }

    public LiveData<UserInformation> getUsername()
    {
        return username;
    }
}
