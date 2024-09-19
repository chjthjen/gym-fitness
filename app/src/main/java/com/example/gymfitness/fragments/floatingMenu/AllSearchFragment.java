package com.example.gymfitness.fragments.floatingMenu;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymfitness.R;
import com.example.gymfitness.adapters.CustomAdapterListViewWorkoutSearch;
import com.example.gymfitness.adapters.WorkoutAdapter;
import com.example.gymfitness.adapters.home.RecommendExRCVApdater;
import com.example.gymfitness.adapters.resources.ArticleResourceAdapter;
import com.example.gymfitness.data.entities.Article;
import com.example.gymfitness.data.entities.Workout;
import com.example.gymfitness.databinding.FragmentAllSearchBinding;
import com.example.gymfitness.utils.Resource;
import com.example.gymfitness.viewmodels.HomeViewModel;
import com.example.gymfitness.viewmodels.SharedViewModel;
import com.example.gymfitness.viewmodels.WorkoutViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class AllSearchFragment extends Fragment {

    private EditText edtSearch;
    private List<String> searchHistory;
    private CustomAdapterListViewWorkoutSearch adapter;
    private SharedPreferences sharedPreferences;

    private WorkoutViewModel workoutViewModel;
    private HomeViewModel articleViewModel;

    private RecyclerView recyclerView;
    private RecyclerView recommendRecyclerView;
    private WorkoutAdapter workoutAdapter;
    private ArticleResourceAdapter articleAdapter;

    private NavController navController;
    private boolean isWorkoutSearch = true;  // Toggle between workout and article search
    private FragmentAllSearchBinding binding;
    private Button btnAll;
    private RecommendExRCVApdater recommendExRCVApdater;
    private SharedViewModel sharedViewModel;



    public AllSearchFragment() {
        super(R.layout.fragment_all_search);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAllSearchBinding.inflate(inflater, container, false);

        edtSearch = binding.edtSearch;
        recyclerView = binding.rcvFavorites;
        recommendRecyclerView = binding.rcvRecommendations;
        btnAll = binding.btnAll;

        sharedPreferences = requireContext().getSharedPreferences("SearchHistory", getContext().MODE_PRIVATE);
        searchHistory = loadSearchHistory();
        adapter = new CustomAdapterListViewWorkoutSearch(getActivity(), searchHistory.toArray(new String[0]));

        workoutViewModel = new ViewModelProvider(requireActivity()).get(WorkoutViewModel.class);
        articleViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        // Initialize RecyclerView adapters
        workoutAdapter = new WorkoutAdapter(new ArrayList<>());
        articleAdapter = new ArticleResourceAdapter(new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recommendExRCVApdater = new RecommendExRCVApdater(new ArrayList<>());
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // Default: show workout data
        recommendRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recommendRecyclerView.setAdapter(recommendExRCVApdater);
        recyclerView.setAdapter(workoutAdapter);
        observeWorkoutData();

        // Set up navigation controller
        workoutViewModel.getWorkouts().observe(getViewLifecycleOwner(), new Observer<ArrayList<Workout>>() {
            @Override
            public void onChanged(ArrayList<Workout> workouts) {
                recommendExRCVApdater.setWorkoutList(workouts);
                recommendExRCVApdater.notifyDataSetChanged();
            }
        });

        // Search actions
        edtSearch.setOnEditorActionListener((v, actionId, event) -> {
            String searchText = edtSearch.getText().toString().trim();
            if (!TextUtils.isEmpty(searchText)) {
                if (!searchHistory.contains(searchText)) {
                    searchHistory.add(0, searchText);
                    adapter.notifyDataSetChanged();
                    saveSearchHistory(searchHistory);
                }
                if (isWorkoutSearch) {
                    workoutViewModel.searchWorkouts(searchText);
                } else {
                    articleViewModel.searchArticles(searchText);
                }
            }
            return true;
        });

        // Button to switch to workout search
        Button btnNutrition = binding.btnNutrition;
        btnNutrition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_allSearchFragment_to_articlesearch);
            }
        });

        Button btnWorkout = binding.btnWorkout;
        btnWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_allSearchFragment_to_workoutSearch_6_3_2_A);
            }
        });

        // Button to show all search results
        btnAll.setOnClickListener(v -> {
        });

        return binding.getRoot();
    }

    // Observe workout data from WorkoutViewModel
    private void observeWorkoutData() {
        workoutViewModel.getWorkouts().observe(getViewLifecycleOwner(), new Observer<ArrayList<Workout>>() {
            @Override
            public void onChanged(ArrayList<Workout> workouts) {
                workoutAdapter.setWorkoutList(workouts);
                workoutAdapter.notifyDataSetChanged();
            }
        });
    }

    // Observe article data from HomeViewModel
    private void observeArticleData() {
        articleViewModel.getArticles().observe(getViewLifecycleOwner(), new Observer<Resource<ArrayList<Article>>>() {
            @Override
            public void onChanged(Resource<ArrayList<Article>> resource) {
                if (resource instanceof Resource.Success) {
                    ArrayList<Article> articles = ((Resource.Success<ArrayList<Article>>) resource).getData();
                    articleAdapter.setArticleList(articles);
                    articleAdapter.notifyDataSetChanged();
                } else if (resource instanceof Resource.Error) {
                    Log.e("ArticleSearchError", ((Resource.Error<ArrayList<Article>>) resource).getMessage());
                }
            }
        });
    }

    // Save search history
    private void saveSearchHistory(List<String> history) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("history", new HashSet<>(history));
        editor.apply();
    }

    // Load search history
    private List<String> loadSearchHistory() {
        Set<String> historySet = sharedPreferences.getStringSet("history", new HashSet<>());
        return new ArrayList<>(historySet);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        workoutAdapter.setOnItemClickListener(workout -> {
            navController.navigate(R.id.action_allSearchFragment_to_exerciseRoutineFragment);
        });


        articleAdapter.setOnItemClickListener(article -> {
            Bundle bundle = new Bundle();
            bundle.putString("articleTitle", article.getArticle_title());
            navController.navigate(R.id.action_allSearchFragment_to_articleDetailFragment2, bundle);
        });
        recommendExRCVApdater.setOnItemClickListener(new RecommendExRCVApdater.OnWorkoutRCMListener() {
            @Override
            public void onItemClick(Workout workout) {
                Log.d("HomeFragment", "Selected workout: " + workout.toString());
                sharedViewModel.select(workout);
                navController.navigate(R.id.action_allSearchFragment_to_exerciseRoutineFragment);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Search");
    }
}