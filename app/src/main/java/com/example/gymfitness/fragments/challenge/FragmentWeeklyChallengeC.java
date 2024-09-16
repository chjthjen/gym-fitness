package com.example.gymfitness.fragments.challenge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.gymfitness.R;
import com.example.gymfitness.databinding.FragmentExerciseDetailBinding;

public class FragmentWeeklyChallengeC extends Fragment {

    FragmentExerciseDetailBinding exerciseDetailBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        exerciseDetailBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_exercise_detail,container,false);

        return exerciseDetailBinding.getRoot();
    }
}
