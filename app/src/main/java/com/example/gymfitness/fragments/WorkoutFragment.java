package com.example.gymfitness.fragments;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.gymfitness.R;
import com.example.gymfitness.adapters.WorkoutAdapter;
import com.example.gymfitness.data.entities.Round;
import com.example.gymfitness.data.entities.Workout;
import com.example.gymfitness.databinding.FragmentWorkoutBinding;
import com.example.gymfitness.viewmodels.SharedViewModel;
import com.example.gymfitness.viewmodels.WorkoutViewModel;

import java.util.ArrayList;
import java.util.Objects;

public class WorkoutFragment extends Fragment {
    private FragmentWorkoutBinding binding;
    private WorkoutViewModel workoutViewModel;
    private ArrayList<Workout> list = new ArrayList<>();
    private WorkoutAdapter workoutAdapter;
    private NavController navController;
    private SharedViewModel sharedViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_workout, container, false);
        workoutViewModel = new ViewModelProvider(requireActivity()).get(WorkoutViewModel.class);
        workoutViewModel.loadWorkouts();
        workoutAdapter = new WorkoutAdapter(new ArrayList<>());
        binding.rvWorkoutItem.setAdapter(workoutAdapter);
        binding.rvWorkoutItem.setLayoutManager(new LinearLayoutManager(getContext()));
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        workoutViewModel.getWorkouts().observe(getViewLifecycleOwner(), new Observer<ArrayList<Workout>>() {
            @Override
            public void onChanged(ArrayList<Workout> workouts) {
                if (workouts != null && !workouts.isEmpty()) {
                    workoutAdapter.setWorkoutList(workouts);
                    workoutAdapter.notifyDataSetChanged();
                } else {
                    Log.d(TAG, "List is empty or null");
                }
            }
        });



        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Workout");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        workoutAdapter.setOnItemClickListener(new WorkoutAdapter.OnWorkoutListener() {
            @Override
            public void onItemClick(Workout workout) {
                sharedViewModel.select(workout);
                navController.navigate(R.id.action_workoutFragment_to_exerciseRoutineFragment);
            }
        });
    }
}
