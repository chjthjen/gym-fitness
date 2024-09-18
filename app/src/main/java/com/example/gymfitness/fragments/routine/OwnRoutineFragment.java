package com.example.gymfitness.fragments.routine;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.example.gymfitness.R;
import com.example.gymfitness.adapters.ExerciseInOwnRoutineAdapter;
import com.example.gymfitness.data.entities.Exercise;
import com.example.gymfitness.data.entities.RoutineRound;
import com.example.gymfitness.databinding.FragmentOwnRoutineBinding;
import com.example.gymfitness.viewmodels.OwnRoutineViewModel;

import java.util.List;
import java.util.Objects;

public class OwnRoutineFragment extends Fragment {
    private FragmentOwnRoutineBinding binding;
    private OwnRoutineViewModel ownRoutineViewModel;
    private NavController navController;
    private ExerciseInOwnRoutineAdapter adapter;

    public OwnRoutineFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOwnRoutineBinding.inflate(inflater, container, false);
        ownRoutineViewModel = new ViewModelProvider(this).get(OwnRoutineViewModel.class);
        binding.setOwnRoutineViewModel(ownRoutineViewModel);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        ownRoutineViewModel.getRoutineRoundsLiveData().observe(getViewLifecycleOwner(), new Observer<List<RoutineRound>>() {
            @Override
            public void onChanged(List<RoutineRound> routineRounds) {
                binding.roundContainer.removeAllViews();
                for (RoutineRound routineRound : routineRounds) {
                    addExistingRound(routineRound.getName(), routineRound.getId());
                }
            }
        });

        binding.btnCreateRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int roundCount = binding.roundContainer.getChildCount();
                String roundName = "Round " + (roundCount + 1);
                ownRoutineViewModel.addRoutineRound(roundName);
            }
        });
    }

    private void addExistingRound(String roundName, int roundId) {
        View newRoundView = LayoutInflater.from(getContext()).inflate(R.layout.custom_add_round_in_routine, null);
        TextView tvRound = newRoundView.findViewById(R.id.tvRound);
        tvRound.setText(roundName);
        binding.roundContainer.addView(newRoundView);

        newRoundView.findViewById(R.id.btnDeleteRound).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.roundContainer.removeView(newRoundView);
                ownRoutineViewModel.removeRoutineRound(roundId); // Xóa khỏi db và cập nhật lại thứ tự
            }
        });

        newRoundView.findViewById(R.id.btnCreateNewRoutine).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("roundId", roundId);
                navController.navigate(R.id.action_ownRoutineFragment_to_createExerciseForOwnRoutineFragment, bundle);
            }
        });
;
        GridView gvExercises = newRoundView.findViewById(R.id.gvExercises);
        ownRoutineViewModel.getExercisesForRound(roundId).observe(getViewLifecycleOwner(), exercises -> {
            adapter = new ExerciseInOwnRoutineAdapter(getContext(), exercises, new ExerciseInOwnRoutineAdapter.ExerciseRemoveListener() {
                @Override
                public void onExerciseRemoved(Exercise exercise) {
                    ownRoutineViewModel.removeExerciseFromRoutineRound(exercise, roundId);
                }
            });
            gvExercises.setAdapter(adapter);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Your Routine");
    }
}