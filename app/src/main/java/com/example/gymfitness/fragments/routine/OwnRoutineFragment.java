package com.example.gymfitness.fragments.routine;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.gymfitness.R;
import com.example.gymfitness.adapters.WeeklyChallenge.RoundAdapter;
import com.example.gymfitness.data.entities.RoutineRound;
import com.example.gymfitness.databinding.FragmentOwnRoutineBinding;
import com.example.gymfitness.viewmodels.OwnRoutineViewModel;

import java.util.List;

public class OwnRoutineFragment extends Fragment {
    int roundCount = 0;
    private FragmentOwnRoutineBinding binding;
    private OwnRoutineViewModel ownRoutineViewModel;

    public OwnRoutineFragment() {

    }

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

        ownRoutineViewModel.getRoutineRounds().observe(getViewLifecycleOwner(), new Observer<List<RoutineRound>>() {
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

        newRoundView.findViewById(R.id.btnEditRound).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        newRoundView.findViewById(R.id.btnDeleteRound).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.roundContainer.removeView(newRoundView);
                ownRoutineViewModel.removeRoutineRound(roundId); // Xóa khỏi db và cập nhật lại thứ tự
            }
        });
    }
}