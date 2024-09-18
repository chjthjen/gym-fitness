package com.example.gymfitness.fragments;

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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.gymfitness.R;
import com.example.gymfitness.adapters.WorkoutAdapter;
import com.example.gymfitness.data.entities.Workout;
import com.example.gymfitness.databinding.FragmentAllWorkoutBinding;
import com.example.gymfitness.utils.UserData;
import com.example.gymfitness.viewmodels.SharedViewModel;
import com.example.gymfitness.viewmodels.WorkoutViewModel;

import java.util.ArrayList;
import java.util.Objects;

public class WorkoutAllFragment extends Fragment {
    private FragmentAllWorkoutBinding binding;
    private WorkoutViewModel workoutViewModel;
    private WorkoutAdapter workoutAdapter;
    private NavController navController;
    private SharedViewModel sharedViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_workout, container, false);
        workoutViewModel = new ViewModelProvider(requireActivity()).get(WorkoutViewModel.class);
        workoutAdapter = new WorkoutAdapter(new ArrayList<>());
        binding.rvWorkoutItem.setAdapter(workoutAdapter);
        binding.rvWorkoutItem.setLayoutManager(new LinearLayoutManager(getContext()));
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        workoutViewModel.getWorkouts().observe(getViewLifecycleOwner(), workouts -> {
            if (workouts != null) {
                workoutAdapter.setWorkoutList(workouts);
            } else {
                Log.d("WorkoutAllFragment", "Workouts list is null");
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Resources");
        String userLevel = UserData.getUserLevel(getContext());
        ColorStateList colorStateList = ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.limegreen));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        workoutViewModel.loadAllWorkouts();
        workoutAdapter.setOnItemClickListener(new WorkoutAdapter.OnWorkoutListener() {
            @Override
            public void onItemClick(Workout workout) {
                sharedViewModel.select(workout);
                navController.navigate(R.id.action_workoutAllFragment_to_exerciseRoutineFragment);
            }
        });
    }
}