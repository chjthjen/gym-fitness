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

public class ArticleViewModel extends ViewModel {
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Workout");
    private MutableLiveData<ArrayList<Article>> articlesLiveData = new MutableLiveData<>();
    private FirebaseRepository firebaseRepository = new FirebaseRepository();
    public LiveData<ArrayList<Article>> getArticles() {
        return articlesLiveData;
    }
    private String userLevel;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private FitnessDB fitnessDB;


    public void loadArticlesByFavorite( Context context) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Article> articleList = new ArrayList<>();
                for (DataSnapshot articleSnapshot : dataSnapshot.getChildren()) {
                    Article article = articleSnapshot.getValue(Article.class);
                    article.setArticle_content(articleSnapshot.getKey());
                    ArrayList<Round> roundsList = new ArrayList<>();
                        for (DataSnapshot roundSnapshot : articleSnapshot.child("round").getChildren()) {
                        Round round = roundSnapshot.getValue(Round.class);
                        round.setRound_name(roundSnapshot.getKey());
                        ArrayList<Exercise> exercisesList = new ArrayList<>();
                        for (DataSnapshot exerciseSnapshot : roundSnapshot.getChildren()) {
                            Exercise exercise = exerciseSnapshot.getValue(Exercise.class);
                            exercise.setExercise_name(exerciseSnapshot.getKey());
                            exercisesList.add(exercise);
                        }
                        round.setExercises(exercisesList);
                        roundsList.add(round);
                    }
                    article.setRound(roundsList);
                    List<FavoriteArticle> lsFavoriteArticles = FavoriteHelper.getListFavoriteArticle(context);
                    for(FavoriteArticle fArticle : lsFavoriteArticles)
                    {
                        if(Objects.equals(article.getArticle_title(), fArticle.getArticle_name()))
                            articleList.add(article);
                    }

                }
                articlesLiveData.setValue(articleList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("WorkoutViewModel", "Database error: " + databaseError.getMessage());
            }
        });
    }
}
