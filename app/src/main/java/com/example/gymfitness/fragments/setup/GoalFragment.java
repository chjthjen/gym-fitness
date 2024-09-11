package com.example.gymfitness.fragments.setup;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.gymfitness.R;
import com.example.gymfitness.databinding.FragmentGoalBinding;
import com.example.gymfitness.viewmodels.SetUpViewModel;

public class GoalFragment extends Fragment {

    private FragmentGoalBinding binding;
    private SetUpViewModel setUpViewModel;

    public GoalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_goal,container,false);
        setUpViewModel = new ViewModelProvider(requireActivity()).get(SetUpViewModel.class);
        setUpViewModel.setGoal("Gain weight");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.rdGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.loseweight)
                    setUpViewModel.setGoal("Lose weight");
                else if(checkedId == R.id.gainweight)
                    setUpViewModel.setGoal("Gain weight");
                else if(checkedId == R.id.musclemassgain)
                    setUpViewModel.setGoal("Muscle mass gain");
                else if(checkedId == R.id.gainweight)
                    setUpViewModel.setGoal("Shape body");
                else
                    setUpViewModel.setGoal("Others");
            }
        });
    }
}