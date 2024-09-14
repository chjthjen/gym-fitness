package com.example.gymfitness.fragments.WeeklyChallenge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymfitness.R;
import com.example.gymfitness.adapters.WeeklyChallenge.RoundAdapter;
import com.example.gymfitness.data.Exercise;

import java.util.ArrayList;
import java.util.List;

public class FragmentWeeklyChallengeB extends Fragment {

    private RecyclerView rvRound1, rvRound2;
    private List<Exercise> round1Exercises, round2Exercises;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weekly_challenge_b, container, false);

        // Initialize RecyclerViews
        rvRound1 = view.findViewById(R.id.rvRound1);
        rvRound2 = view.findViewById(R.id.rvRound2);

        // Prepare exercise data
        round1Exercises = getRound1Exercises();
        round2Exercises = getRound2Exercises();

        // Set adapters
        rvRound1.setLayoutManager(new LinearLayoutManager(getContext()));
        rvRound1.setAdapter(new RoundAdapter(round1Exercises, getContext()));

        rvRound2.setLayoutManager(new LinearLayoutManager(getContext()));
        rvRound2.setAdapter(new RoundAdapter(round2Exercises, getContext()));

        return view;
    }
    private List<Exercise> getRound1Exercises() {
        // Add mock data for round 1 exercises
        List<Exercise> exercises = new ArrayList<>();
        exercises.add(new Exercise("Dumbbell Rows", "00:30", "Repetition 4x", R.drawable.ic_play_video));
        // Add more exercises as needed
        return exercises;
    }

    private List<Exercise> getRound2Exercises() {
        // Add mock data for round 2 exercises
        List<Exercise> exercises = new ArrayList<>();
        exercises.add(new Exercise("Push-ups", "00:45", "Repetition 4x", R.drawable.ic_play_video));
        // Add more exercises as needed
        return exercises;
    }
}
