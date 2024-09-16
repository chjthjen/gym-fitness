package com.example.gymfitness.fragments.routine;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gymfitness.R;
import com.example.gymfitness.adapters.ExerciseForOwnRoutineAdapter;
import com.example.gymfitness.data.entities.Exercise;
import com.example.gymfitness.databinding.FragmentCreateExerciseForOwnRoutineBinding;
import com.example.gymfitness.viewmodels.OwnRoutineViewModel;

public class CreateExerciseForOwnRoutineFragment extends Fragment {
    private ExerciseForOwnRoutineAdapter adapter;
    private int roundId = 0;

    public CreateExerciseForOwnRoutineFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentCreateExerciseForOwnRoutineBinding binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_create_exercise_for_own_routine, container, false);
        OwnRoutineViewModel ownRoutineViewModel = new ViewModelProvider(this).get(OwnRoutineViewModel.class);

//        binding.setOwnRoutineViewModel(ownRoutineViewModel);
//        binding.setLifecycleOwner(this);

        // Nhận RoundId tu Bundle
        Bundle bundle = getArguments();

        if (bundle != null) {
            roundId = bundle.getInt("roundId", 0);
        }

        // Lấy Exercises từ Firebase
        ownRoutineViewModel.getExercisesFromFirebase();
        ownRoutineViewModel.getExercises().observe(getViewLifecycleOwner(), exercises -> {
            // Cập nhật GridView
            adapter = new ExerciseForOwnRoutineAdapter(exercises, inflater, new ExerciseForOwnRoutineAdapter.ExerciseAddListener() {
                @Override
                public void onExerciseAdded(Exercise exercise) {
                    ownRoutineViewModel.addExerciseToRound(exercise, roundId);
                }
            });

            binding.gridViewExercise.setAdapter(adapter);
        });

        return binding.getRoot();
    }
}