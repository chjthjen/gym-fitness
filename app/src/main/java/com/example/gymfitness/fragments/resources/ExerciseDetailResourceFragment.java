package com.example.gymfitness.fragments.resources;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.gymfitness.R;
import com.example.gymfitness.databinding.FragmentExerciseDetailBinding;
import com.example.gymfitness.viewmodels.SharedViewModel;

public class ExerciseDetailResourceFragment extends Fragment {
    private FragmentExerciseDetailBinding binding;
    private SharedViewModel sharedViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_exercise_detail, container, false);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        return binding.getRoot();
    }
}
