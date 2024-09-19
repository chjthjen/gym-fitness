package com.example.gymfitness.fragments.WeeklyChallenge;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import com.bumptech.glide.Glide;
import com.example.gymfitness.R;
import com.example.gymfitness.activities.HomeActivity;
import com.example.gymfitness.adapters.home.RoundRCVAdapter;
import com.example.gymfitness.data.entities.Round;
import com.example.gymfitness.data.entities.Workout;
import com.example.gymfitness.databinding.FragmentChallengeCompetitionsBBinding;
import com.example.gymfitness.databinding.FragmentExerciseRoutineBinding;
import com.example.gymfitness.databinding.FragmentWeeklyChallengeABinding;
import com.example.gymfitness.utils.UserData;
import com.example.gymfitness.viewmodels.ExerciseRoutineViewModel;
import com.example.gymfitness.viewmodels.SharedViewModel;
import com.example.gymfitness.viewmodels.WorkoutViewModel;

import java.util.ArrayList;
import java.util.Objects;

public class WeeklyChallengeAFragment extends Fragment {
    private FragmentWeeklyChallengeABinding binding;
    private WorkoutViewModel viewModel;
    private SharedViewModel sharedViewModel;
    private NavController navController;
    private Workout getWorkout = new Workout();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_weekly_challenge_a, container, false);

        viewModel=new ViewModelProvider(requireActivity()).get(WorkoutViewModel.class);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        return binding.getRoot();
    }
    private void loadData() {
        sharedViewModel.getSelected().observe(getViewLifecycleOwner(), new Observer<Workout>() {
            @Override
            public void onChanged(Workout workout) {
                if (workout != null) {
                    // Hiển thị tên bài tập
                    binding.tvNameChallengesCompetitons.setText(workout.getWorkout_name());
                    // Lưu lại workout nhận được
                    getWorkout = workout;
                    Log.d("WeeklyChallengeAFragment", "Received workout: " + workout.getWorkout_name());
                } else {
                    Log.d("WeeklyChallengeAFragment", "No workout data received.");
                }
            }
        });
    }



    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
        ((HomeActivity) requireActivity()).getBottomNavigationView().setVisibility(View.GONE);
        loadData();
    }
    @Override
    public void onPause() {
        super.onPause();
        ((HomeActivity) requireActivity()).getBottomNavigationView().setVisibility(View.VISIBLE);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        binding.buttonStartNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedViewModel.select(getWorkout);
                Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
                navController.navigate(R.id.action_weeklyChallengeAFragment_to_weeklyChallengeBFragment);

            }

        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });

    }
}
