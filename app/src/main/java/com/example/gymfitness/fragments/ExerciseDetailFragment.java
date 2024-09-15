package com.example.gymfitness.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.gymfitness.R;
import com.example.gymfitness.adapters.home.RoundRCVAdapter;
import com.example.gymfitness.data.entities.Exercise;
import com.example.gymfitness.data.entities.Round;
import com.example.gymfitness.data.entities.Workout;
import com.example.gymfitness.databinding.FragmentExerciseDetailBinding;
import com.example.gymfitness.utils.UserData;
import com.example.gymfitness.viewmodels.SharedViewModel;

import java.util.Objects;

public class ExerciseDetailFragment extends Fragment {

    FragmentExerciseDetailBinding exerciseDetailBinding;
    SharedViewModel sharedViewModel;
    private String level;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        exerciseDetailBinding= DataBindingUtil.inflate(inflater, R.layout.fragment_exercise_detail, container, false);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        level = UserData.getUserLevel(getContext());

        return exerciseDetailBinding.getRoot();
    }

    private void loadData()
    {
        sharedViewModel.getExerciseSelected().observe(getViewLifecycleOwner(), new Observer<Exercise>() {
            @Override
            public void onChanged(Exercise exercise) {
                Glide.with(exerciseDetailBinding.thumbnail.getContext())
                        .load(exercise.getExerciseThumb())
                        .placeholder(R.drawable.woman_helping_man_gym)
                        .error(R.drawable.woman_helping_man_gym)
                        .into(exerciseDetailBinding.thumbnail);
                exerciseDetailBinding.exerciseName.setText(exercise.getExercise_name());
                exerciseDetailBinding.duration.setText(String.valueOf(exercise.getDuration()) + " Seconds");
                exerciseDetailBinding.rep.setText(String.valueOf(exercise.getRep()) + " Rep");
                exerciseDetailBinding.level.setText(exercise.getLevel());
            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(level);
        loadData();
    }
}