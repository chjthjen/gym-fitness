package com.example.gymfitness.fragments.search;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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
import com.example.gymfitness.data.entities.Workout;
import com.example.gymfitness.viewmodels.SharedViewModel;
import com.example.gymfitness.viewmodels.WorkoutViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class WorkoutSearchFragment extends Fragment {

    private ListView listView;
    private EditText edtSearch;
    private List<String> searchHistory;
    private CustomAdapterListViewWorkoutSearch adapter;
    private SharedPreferences sharedPreferences;

    private WorkoutViewModel workoutViewModel;
    private RecyclerView rvWorkoutItem;
    private WorkoutAdapter workoutAdapter;
    private NavController navController;
    private SharedViewModel sharedViewModel;

    public WorkoutSearchFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout_search_6_3_2__a, container, false);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        listView = view.findViewById(R.id.listView);
        edtSearch = view.findViewById(R.id.edtSearch);
        rvWorkoutItem = view.findViewById(R.id.rv_workout_item);

        sharedPreferences = requireContext().getSharedPreferences("SearchHistory", getContext().MODE_PRIVATE);

        workoutViewModel = new ViewModelProvider(requireActivity()).get(WorkoutViewModel.class);

        searchHistory = loadSearchHistory();
        adapter = new CustomAdapterListViewWorkoutSearch(getActivity(), searchHistory.toArray(new String[0]));
        listView.setAdapter(adapter);

        workoutAdapter = new WorkoutAdapter(new ArrayList<>());
        rvWorkoutItem.setLayoutManager(new LinearLayoutManager(getContext()));
        rvWorkoutItem.setAdapter(workoutAdapter);

        workoutViewModel.getWorkouts().observe(getViewLifecycleOwner(), new Observer<ArrayList<Workout>>() {
            @Override
            public void onChanged(ArrayList<Workout> workouts) {
                workoutAdapter.setWorkoutList(workouts);
                workoutAdapter.notifyDataSetChanged();
            }
        });

        edtSearch.setOnEditorActionListener((v, actionId, event) -> {
            String searchText = edtSearch.getText().toString().trim();

            if (!TextUtils.isEmpty(searchText)) {
                if (!searchHistory.contains(searchText)) {
                    searchHistory.add(searchText);
                    adapter = new CustomAdapterListViewWorkoutSearch(getActivity(), searchHistory.toArray(new String[0]));
                    listView.setAdapter(adapter);

                    saveSearchHistory(searchHistory);
                }

                workoutViewModel.searchWorkouts(searchText);
            } else {
                workoutViewModel.searchWorkouts("");
            }

            return true;
        });


        workoutViewModel.setUserLevel(getContext());
        workoutViewModel.loadWorkouts();


        Button btnNutrition = view.findViewById(R.id.btnNutrition);
        btnNutrition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_workoutSearch_6_3_2_A_to_articlesearch);
            }
        });

        return view;
    }

    private void saveSearchHistory(List<String> history) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("history", new HashSet<>(history));
        editor.apply();
    }

    private List<String> loadSearchHistory() {
        Set<String> historySet = sharedPreferences.getStringSet("history", new HashSet<>());
        return new ArrayList<>(historySet);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem itemsSearch = menu.findItem(R.id.ic_search);
        if (itemsSearch != null) {
            itemsSearch.setVisible(false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Search");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        workoutAdapter.setOnItemClickListener(new WorkoutAdapter.OnWorkoutListener() {
            @Override
            public void onItemClick(Workout workout) {
                sharedViewModel.select(workout);
                navController.navigate(R.id.action_workoutSearchFragment_to_exerciseRoutineFragment);
            }
        });
    }
}
