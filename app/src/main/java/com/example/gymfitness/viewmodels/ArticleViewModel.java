package com.example.gymfitness.viewmodels;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gymfitness.data.database.FitnessDB;
import com.example.gymfitness.data.entities.Article;
import com.example.gymfitness.data.entities.Exercise;
import com.example.gymfitness.data.entities.FavoriteArticle;
import com.example.gymfitness.data.entities.FavoriteWorkout;
import com.example.gymfitness.data.entities.Round;
import com.example.gymfitness.data.entities.Workout;
import com.example.gymfitness.firebase.FirebaseRepository;
import com.example.gymfitness.helpers.FavoriteHelper;
import com.example.gymfitness.utils.Resource;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ArticleViewModel extends ViewModel {
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Articles");
    private final MutableLiveData<Resource<ArrayList<Article>>> articlesLiveData = new MutableLiveData<>();
    private FirebaseRepository firebaseRepository = new FirebaseRepository();
    public LiveData<Resource<ArrayList<Article>>> getArticles() {
        return articlesLiveData;
    }
    private String userLevel;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private FitnessDB fitnessDB;


    public void loadArticlesByFavorite(Context context) {
        articlesLiveData.setValue(new Resource.Loading<>());
        firebaseRepository.getFavoriteArticles(new FirebaseRepository.ArticleCallback() {
            @Override
            public void onCallback(List<Article> articles) {
                Resource<ArrayList<Article>> resource = new Resource.Success<>(new ArrayList<>(articles));
                articlesLiveData.postValue(resource);
                Log.d("TAG", "onCallback: " + resource.getData());
            }

            @Override
            public void onError(DatabaseError error) {
                articlesLiveData.setValue(new Resource.Error<>(error.getMessage()));
            }
        }, context);
    }
}
