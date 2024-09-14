package com.example.gymfitness.fragments;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import com.example.gymfitness.R;
import com.example.gymfitness.adapters.home.ArticlesTipsRCVAdapter;
import com.example.gymfitness.data.entities.Article;
import com.example.gymfitness.data.entities.Round;
import com.example.gymfitness.data.entities.Workout;
import com.example.gymfitness.viewmodels.HomeViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;

import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymfitness.adapters.home.RecommendExRCVApdater;
import com.example.gymfitness.data.WorkoutTest;
import com.example.gymfitness.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecommendExRCVApdater recommendExRCVApdater;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.LayoutManager layoutManager2;
    private ArticlesTipsRCVAdapter articlesTipsRCVAdapter;

    private HomeViewModel homeViewModel;

    private NavController navController;
    private ExecutorService executorService;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        homeViewModel.setUserLevel(getContext());
        executorService = Executors.newFixedThreadPool(3);

        return binding.getRoot();
    }
    private void setupRecyclerView() {
        RecyclerView recyclerView = binding.rcvRecommendations;
        recommendExRCVApdater = new RecommendExRCVApdater(new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(recommendExRCVApdater);

        homeViewModel.getWorkouts().observe(getViewLifecycleOwner(), new Observer<ArrayList<Workout>>() {
            @Override
            public void onChanged(ArrayList<Workout> workouts) {
                if (workouts != null && !workouts.isEmpty()) {
                    recommendExRCVApdater.setWorkoutList(workouts);
                } else {
                    Log.d("hello", "List is empty or null");
                }
            }
        });
    }
    private void setupArticlesRecyclerView() {
        RecyclerView recyclerView = binding.rcvArticlesTips;
        articlesTipsRCVAdapter = new ArticlesTipsRCVAdapter(new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(articlesTipsRCVAdapter);

        homeViewModel.getArticles().observe(getViewLifecycleOwner(), new Observer<ArrayList<Article>>() {
            @Override
            public void onChanged(ArrayList<Article> articles) {
                if (articles != null && !articles.isEmpty()) {
                    articlesTipsRCVAdapter.setArticleList(articles);
                } else {
                    Log.d("hello", "List is empty or null");
                }
            }
        });
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        binding.imgWorkout.setOnClickListener(v -> navController.navigate(R.id.action_homeFragment_to_workoutFragment));
        homeViewModel.loadWorkoutsByLevel();
        homeViewModel.loadArticles();

        setupRecyclerView();
        setupArticlesRecyclerView();



    }

}
