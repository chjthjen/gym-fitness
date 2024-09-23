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
import com.example.gymfitness.data.entities.RoutineRound;
import com.example.gymfitness.databinding.FragmentDeleteExerciseInRoutineBinding;
import com.example.gymfitness.databinding.FragmentDeleteRoundInRoutineBinding;
import com.example.gymfitness.viewmodels.OwnRoutineViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class DeleteRoundInRoutineFragment extends BottomSheetDialogFragment {
    private FragmentDeleteRoundInRoutineBinding binding;
    private String round_name;
    private int roundId;
    private OwnRoutineViewModel ownRoutineViewModel;

    public DeleteRoundInRoutineFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_delete_round_in_routine, container, false);
        ownRoutineViewModel = new ViewModelProvider(requireActivity()).get(OwnRoutineViewModel.class);
        binding.setViewModel(ownRoutineViewModel);
        if(getArguments() != null){
            roundId = getArguments().getInt("round_id");
            round_name = getArguments().getString("round_name");
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RoutineRound routineRound = new RoutineRound();
        routineRound.setId(roundId);
        binding.message.setText("Are you sure want to delete " + round_name+ " ?");
        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        binding.btnAcceptDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ownRoutineViewModel.removeRoutineRound(routineRound.getId());
                dismiss();
            }
        });
    }
}