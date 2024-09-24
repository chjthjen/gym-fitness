package com.example.gymfitness.fragments.routine;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gymfitness.R;
import com.example.gymfitness.data.entities.Exercise;
import com.example.gymfitness.databinding.FragmentDeleteExerciseInRoutineBinding;
import com.example.gymfitness.viewmodels.OwnRoutineViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class DeleteExerciseInRoutineFragment extends BottomSheetDialogFragment {
    private FragmentDeleteExerciseInRoutineBinding binding;
    private String exercise_name;
    private int roundId;
    private OwnRoutineViewModel ownRoutineViewModel;

    public DeleteExerciseInRoutineFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_delete_exercise_in_routine, container, false);
        ownRoutineViewModel = new ViewModelProvider(requireActivity()).get(OwnRoutineViewModel.class);
        binding.setViewModel(ownRoutineViewModel);
        if(getArguments() != null){
            exercise_name = getArguments().getString("exercise_name");
            roundId = getArguments().getInt("round_id");
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.message.setText("Are you sure want to delete " + exercise_name + " ?");
        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        binding.btnAcceptDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Exercise exercise = new Exercise();
                exercise.setExercise_name(exercise_name);
                ownRoutineViewModel.removeExerciseFromRoutineRound(exercise, roundId);
                dismiss();
            }
        });
    }
}