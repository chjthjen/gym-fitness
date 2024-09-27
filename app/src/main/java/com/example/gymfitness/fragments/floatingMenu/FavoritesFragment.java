package com.example.gymfitness.fragments.floatingMenu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.gymfitness.R;
import com.example.gymfitness.adapters.WorkoutAdapter;
import com.example.gymfitness.adapters.favorites.FavoriteArticlesResourceAdapter;
import com.example.gymfitness.adapters.favorites.FavoriteWorkoutsAdapter;
import com.example.gymfitness.adapters.home.ArticlesTipsRCVAdapter;
import com.example.gymfitness.adapters.resources.ArticleResourceAdapter;
import com.example.gymfitness.data.entities.Article;
import com.example.gymfitness.data.entities.FavoriteArticle;
import com.example.gymfitness.data.entities.FavoriteWorkout;
import com.example.gymfitness.data.entities.Workout;
import com.example.gymfitness.databinding.FragmentFavoritesBinding;
import com.example.gymfitness.firebase.FirebaseRepository;
import com.example.gymfitness.utils.SwipeToDeleteCallback;
import com.example.gymfitness.viewmodels.ArticleDetailViewModel;
import com.example.gymfitness.viewmodels.ArticleViewModel;
import com.example.gymfitness.viewmodels.SharedViewModel;
import com.example.gymfitness.viewmodels.WorkoutViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class FavoritesFragment extends Fragment {
    private FragmentFavoritesBinding binding;
    private ArticleViewModel articleViewModel;
    private FavoriteArticlesResourceAdapter articleResourceAdapter;
    private FavoriteWorkoutsAdapter workoutAdapter;
    private WorkoutViewModel workoutViewModel;
    private SharedViewModel sharedViewModel;

    NavController navController;
    private int current_sort = 0;
    private ColorStateList colorStateList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorites, container, false);
        articleViewModel = new ArticleViewModel();
        articleResourceAdapter = new FavoriteArticlesResourceAdapter(new ArrayList<>());
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        workoutViewModel = new WorkoutViewModel();
        workoutAdapter = new FavoriteWorkoutsAdapter(new ArrayList<>());
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Favorites");
        binding.btnAll.callOnClick();
    }

    void setUpArticlesRecyclerView() {
        binding.rcvFavorites2.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rcvFavorites2.setAdapter(articleResourceAdapter);
    }

    void setUpWorkoutRecycleView(){
        binding.rcvFavorites.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rcvFavorites.setAdapter(workoutAdapter);
    }

    void removeArticleRecyclerView() {
        articleResourceAdapter.setArticleList(new ArrayList<>());
        binding.rcvFavorites2.setAdapter(null);
    }
    private void removeWorkoutRecyclerView() {
        workoutAdapter.setWorkoutList(new ArrayList<>());
        binding.rcvFavorites.setAdapter(null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        binding.btnAll.setOnClickListener(v -> sortChange(0));
        binding.btnVideo.setOnClickListener(v -> sortChange(1));
        binding.btnArticle.setOnClickListener(v -> sortChange(2));

        workoutAdapter.setOnItemClickListener(new WorkoutAdapter.OnWorkoutListener() {
            @Override
            public void onItemClick(Workout workout) {
                sharedViewModel.select(workout);
                navController.navigate(R.id.action_favoritesFragment_to_fragmentWeeklyChallengeB);
            }
        });

        articleResourceAdapter.setOnItemClickListener(new ArticleResourceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Article article) {
                Bundle bundle = new Bundle();
                bundle.putString("articleTitle", article.getArticle_title());
                navController.navigate(R.id.action_favoritesFragment_to_articleDetailFragment2, bundle);
            }
        });


        attachItemTouchHelperArticles();
        attachItemTouchHelperWorkouts();
    }

    void attachItemTouchHelperArticles() {
        ItemTouchHelper itemTouchHelperArticles = new ItemTouchHelper(new SwipeToDeleteCallback<>(articleResourceAdapter, item -> {
            if (item instanceof Article) {
                FavoriteArticle favoriteArticle = new FavoriteArticle();
                favoriteArticle.setArticle_name(((Article) item).getArticle_title());
                articleViewModel.deleteArticle(getContext(), favoriteArticle);
                Toast.makeText(getContext(), "Article deleted: " + favoriteArticle.getArticle_name(), Toast.LENGTH_SHORT).show();
            }
        }, getContext()));

        itemTouchHelperArticles.attachToRecyclerView(binding.rcvFavorites2);
    }

    void attachItemTouchHelperWorkouts() {
        ItemTouchHelper itemTouchHelperWorkouts = new ItemTouchHelper(new SwipeToDeleteCallback<>(workoutAdapter, item -> {
            if (item instanceof Workout) {
                FavoriteWorkout favoriteWorkout = new FavoriteWorkout();
                favoriteWorkout.setWorkout_name(((Workout) item).getWorkout_name());
                workoutViewModel.deleteWorkout(getContext(), favoriteWorkout);
                Toast.makeText(getContext(), "Workout deleted: " + favoriteWorkout.getWorkout_name(), Toast.LENGTH_SHORT).show();
            }
        }, getContext()));
        itemTouchHelperWorkouts.attachToRecyclerView(binding.rcvFavorites);
    }

    private void sortChange(int pos) {
        colorStateList = ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.white));

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

        colorStateList = ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.limegreen));
        workoutAdapter.setWorkoutList(new ArrayList<>());
        articleResourceAdapter.setArticleList(new ArrayList<>());

        if (pos == 0) {

            binding.btnAll.setBackgroundTintList(colorStateList);
            binding.btnAll.setTextColor(ContextCompat.getColor(getContext(), R.color.black));

            setUpWorkoutRecycleView();
            setUpArticlesRecyclerView();


            loadWorkouts();
            loadArticles();
        } else if (pos == 1) {
            binding.btnVideo.setBackgroundTintList(colorStateList);
            binding.btnVideo.setTextColor(ContextCompat.getColor(getContext(), R.color.black));

            setUpWorkoutRecycleView();
            removeArticleRecyclerView();


            loadWorkouts();
        } else {

            binding.btnArticle.setBackgroundTintList(colorStateList);
            binding.btnArticle.setTextColor(ContextCompat.getColor(getContext(), R.color.black));

            setUpArticlesRecyclerView();
            removeWorkoutRecyclerView();


            loadArticles();
        }

        current_sort = pos;
    }

    private void loadWorkouts() {
        workoutViewModel.loadWorkoutsByFavorite(getContext());
        workoutViewModel.getWorkout().observe(getViewLifecycleOwner(), resource -> {
            switch (resource.getClass().getSimpleName()) {
                case "Loading":
                    Log.d("TAG", "onViewCreated: Loading workouts");
                    break;
                case "Success":
                    workoutAdapter.setWorkoutList(resource.getData());
                    workoutAdapter.notifyDataSetChanged();
                    break;
                case "Error":
                    Log.d("TAG", "onViewCreated: Error loading workouts");
                    break;
            }
        });
    }

    private void loadArticles() {
        articleViewModel.loadArticlesByFavorite(getContext());
        articleViewModel.getArticles().observe(getViewLifecycleOwner(), resource -> {
            switch (resource.getClass().getSimpleName()) {
                case "Loading":
                    Log.d("TAG", "onViewCreated: Loading articles");
                    break;
                case "Success":
                    articleResourceAdapter.setArticleList(resource.getData());
                    articleResourceAdapter.notifyDataSetChanged();
                    break;
                case "Error":
                    Log.d("TAG", "onViewCreated: Error loading articles");
                    break;
            }
        });
    }


}