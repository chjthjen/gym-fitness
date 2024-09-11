package com.example.gymfitness.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gymfitness.R;
import com.example.gymfitness.adapters.ExerciseAdapter;
import com.example.gymfitness.databinding.FragmentExerciseRoutineBinding;
import com.example.gymfitness.viewmodels.ExerciseRoutineViewModel;



public class ExerciseRoutineFragment extends Fragment {

    private ExerciseRoutineViewModel viewModel;
    private FragmentExerciseRoutineBinding binding;

    public ExerciseRoutineFragment() {
        // Required empty public constructor
    }


    public static ExerciseRoutineFragment newInstance(String param1, String param2) {
        return new ExerciseRoutineFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_workout, container, false);
        View view=binding.getRoot();
        viewModel=new ExerciseRoutineViewModel();

        ExerciseAdapter exerciseAdapter1=new ExerciseAdapter(viewModel.getExercises1());
        ExerciseAdapter exerciseAdapter2=new ExerciseAdapter(viewModel.getExercises2());
        binding.rvRound1.setAdapter(exerciseAdapter1);
        binding.rvRound2.setAdapter(exerciseAdapter2);
        return view;
    }
}