package com.example.gymfitness.fragments.challenge;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gymfitness.R;
import com.example.gymfitness.adapters.WorkoutAdapter;
import com.example.gymfitness.adapters.challenges.ChallengesAndCompetitionsRCVAdapter;
import com.example.gymfitness.data.entities.Workout;
import com.example.gymfitness.databinding.FragmentCommunityBinding;
import com.example.gymfitness.viewmodels.SharedViewModel;
import com.example.gymfitness.viewmodels.WorkoutViewModel;

import java.util.ArrayList;
import java.util.Objects;

public class CommunityFragment extends Fragment {

    FragmentCommunityBinding binding;
    private WorkoutViewModel workoutViewModel;
    private ArrayList<Workout> list = new ArrayList<>();
    private ChallengesAndCompetitionsRCVAdapter challengesAndCompetitionsRCVAdapter;
    private NavController navController;
    private SharedViewModel sharedViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_community,container,false);
        workoutViewModel = new ViewModelProvider(requireActivity()).get(WorkoutViewModel.class);
        workoutViewModel.loadWorkouts();
        challengesAndCompetitionsRCVAdapter = new ChallengesAndCompetitionsRCVAdapter(new ArrayList<>());
        binding.rcvWorkoutChallenges.setAdapter(challengesAndCompetitionsRCVAdapter);
        binding.rcvWorkoutChallenges.setLayoutManager(new LinearLayoutManager(getContext()));
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        workoutViewModel.getWorkouts().observe(getViewLifecycleOwner(), new Observer<ArrayList<Workout>>() {
            @Override
            public void onChanged(ArrayList<Workout> workouts) {
                if (workouts != null && !workouts.isEmpty()) {
                    challengesAndCompetitionsRCVAdapter.setWorkoutList(workouts);
                    challengesAndCompetitionsRCVAdapter.notifyDataSetChanged();
                } else {
                    Log.d(TAG, "List is empty or null");
                }
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Community");
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        challengesAndCompetitionsRCVAdapter.setOnItemClickListener(new ChallengesAndCompetitionsRCVAdapter.OnChallengesAndCompetitionsListener() {
            @Override
            public void onItemClick(Workout workout) {
                sharedViewModel.select(workout);
                navController.navigate(R.id.action_communityFragment2_to_fragmentWeeklyChallengeA);
            }
        });
    }

}