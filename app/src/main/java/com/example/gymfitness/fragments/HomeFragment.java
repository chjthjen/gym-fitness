package com.example.gymfitness.fragments;



import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import com.example.gymfitness.R;
import com.example.gymfitness.adapters.home.ArticlesTipsRCVAdapter;
import com.example.gymfitness.data.entities.Article;
import com.example.gymfitness.data.entities.Workout;
import com.example.gymfitness.viewmodels.HomeViewModel;

import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymfitness.adapters.home.RecommendExRCVApdater;

import com.example.gymfitness.databinding.FragmentHomeBinding;

import java.util.ArrayList;

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


        recommendExRCVApdater = new RecommendExRCVApdater(new ArrayList<>());
        articlesTipsRCVAdapter = new ArticlesTipsRCVAdapter(new ArrayList<>());

        setupRecyclerView();
        setupArticlesRecyclerView();

        return binding.getRoot();
    }
    private void setupRecyclerView() {
        RecyclerView recyclerView = binding.rcvRecommendations;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(recommendExRCVApdater);
    }

    private void setupArticlesRecyclerView() {
        RecyclerView recyclerView = binding.rcvArticlesTips;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(articlesTipsRCVAdapter);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        binding.imgWorkout.setOnClickListener(v -> navController.navigate(R.id.action_homeFragment_to_workoutFragment));
        homeViewModel.loadWorkoutsByLevel();
        homeViewModel.loadArticles();
        homeViewModel.getWorkouts().observe(getViewLifecycleOwner(), resource -> {
            switch (resource.getClass().getSimpleName()) {
                case "Loading":
                    binding.progressBarRecommendations.setVisibility(View.VISIBLE);
                    break;
                case "Success":
                    binding.progressBarRecommendations.setVisibility(View.GONE);
                    recommendExRCVApdater.setWorkoutList(resource.getData());
                    break;
                case "Error":
                    binding.progressBarRecommendations.setVisibility(View.GONE);
                    Log.d("hello", resource.getMessage());
                    break;
            }
        });
        homeViewModel.getArticles().observe(getViewLifecycleOwner(), resource -> {
            switch (resource.getClass().getSimpleName()) {
                case "Loading":
                    binding.progressBarArticle.setVisibility(View.VISIBLE);
                    break;
                case "Success":
                    binding.progressBarArticle.setVisibility(View.GONE);
                    articlesTipsRCVAdapter.setArticleList(resource.getData());
                    break;
                case "Error":
                    binding.progressBarArticle.setVisibility(View.GONE);
                    Log.d("hello", resource.getMessage());
                    break;
            }
        });



    }

}
