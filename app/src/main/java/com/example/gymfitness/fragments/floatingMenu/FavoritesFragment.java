package com.example.gymfitness.fragments.floatingMenu;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.gymfitness.R;
import com.example.gymfitness.adapters.WorkoutAdapter;
import com.example.gymfitness.adapters.home.ArticlesTipsRCVAdapter;
import com.example.gymfitness.adapters.resources.ArticleResourceAdapter;
import com.example.gymfitness.data.entities.Article;
import com.example.gymfitness.databinding.FragmentFavoritesBinding;
import com.example.gymfitness.firebase.FirebaseRepository;
import com.example.gymfitness.viewmodels.ArticleViewModel;
import com.example.gymfitness.viewmodels.WorkoutViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class FavoritesFragment extends Fragment {
    FragmentFavoritesBinding binding;
    ArticleViewModel articleViewModel;
    ArticleResourceAdapter articleResourceAdapter;
    WorkoutAdapter workoutAdapter;
    WorkoutViewModel workoutViewModel;
    private int current_sort = 0;
    private ColorStateList colorStateList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorites, container, false);
        articleViewModel = new ArticleViewModel();
        articleResourceAdapter = new ArticleResourceAdapter(new ArrayList<>());

        workoutViewModel = new WorkoutViewModel();
        workoutAdapter = new WorkoutAdapter(new ArrayList<>());
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Favorites");
        //setUpArticlesRecyclerView();
        //setUpWorkoutRecycleView();
    }

    void setUpArticlesRecyclerView() {
        binding.rcvFavorites.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rcvFavorites.setAdapter(articleResourceAdapter);
    }

    void setUpWorkoutRecycleView(){
        binding.rcvFavorites.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rcvFavorites.setAdapter(workoutAdapter);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnAll.setOnClickListener(v -> sortChange(0));
        binding.btnVideo.setOnClickListener(v -> sortChange(1));
        binding.btnArticle.setOnClickListener(v -> sortChange(2));

    }
    private void sortChange(int pos) {
        colorStateList = ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.white));
        // set button is not selected
        if (current_sort == 0) {
            binding.btnAll.setBackgroundTintList(colorStateList);
            binding.btnAll.setTextColor(ContextCompat.getColor(getContext(), R.color.purple));


        } else if (current_sort == 1) {
            binding.btnVideo.setBackgroundTintList(colorStateList);
            binding.btnVideo.setTextColor(ContextCompat.getColor(getContext(), R.color.purple));

        } else {
            binding.btnArticle.setBackgroundTintList(colorStateList);
            binding.btnArticle.setTextColor(ContextCompat.getColor(getContext(), R.color.purple));

        }

        // set selected button
        colorStateList = ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.limegreen));
        if (pos == 0) {
            binding.btnAll.setBackgroundTintList(colorStateList);
            binding.btnAll.setTextColor(ContextCompat.getColor(getContext(), R.color.black));


        } else if (pos == 1) {
            binding.btnVideo.setBackgroundTintList(colorStateList);
            binding.btnVideo.setTextColor(ContextCompat.getColor(getContext(), R.color.black));

            setUpWorkoutRecycleView();
            workoutViewModel.loadWorkoutsByFavorite(getContext());
            workoutViewModel.getWorkout().observe(getViewLifecycleOwner(), resource -> {
                switch (resource.getClass().getSimpleName()) {
                    case "Loading":
                        Log.d("TAG", "onViewCreated: Loading");
                        break;
                    case "Success":
                        Log.d("TAG", "onViewCreated: Success");
                        Log.d("Data", "Workout List: " + resource.getData().toString());
                        workoutAdapter.setWorkoutList(resource.getData());
                        break;
                    case "Error":
                        Log.d("TAG", "onViewCreated: Error");
                        break;
                }
            });
        } else {
            binding.btnArticle.setBackgroundTintList(colorStateList);
            binding.btnArticle.setTextColor(ContextCompat.getColor(getContext(), R.color.black));

            setUpArticlesRecyclerView();
            articleViewModel.loadArticlesByFavorite(getContext());
            articleViewModel.getArticles().observe(getViewLifecycleOwner(), resource -> {
                switch (resource.getClass().getSimpleName()) {
                    case "Loading":
                        Log.d("TAG", "onViewCreated: Loading");
                        break;
                    case "Success":
                        Log.d("TAG", "onViewCreated: Success");
                        articleResourceAdapter.setArticleList(resource.getData());
                        break;
                    case "Error":
                        Log.d("TAG", "onViewCreated: Error");
                        break;
                }
            });
        }
        // update current sort
        current_sort = pos;
    }
}
