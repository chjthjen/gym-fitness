package com.example.gymfitness.fragments.WeeklyChallenge;

import android.os.Bundle;
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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.gymfitness.R;
import com.example.gymfitness.adapters.home.RoundRCVAdapter;
import com.example.gymfitness.data.entities.Round;
import com.example.gymfitness.data.entities.Workout;
import com.example.gymfitness.databinding.FragmentExerciseRoutineBinding;
import com.example.gymfitness.databinding.FragmentWeeklyChallengeBBinding;
import com.example.gymfitness.utils.UserData;
import com.example.gymfitness.viewmodels.ExerciseRoutineViewModel;
import com.example.gymfitness.viewmodels.SharedViewModel;

import java.util.ArrayList;
import java.util.Objects;

public class WeeklyChallengeBFragment extends Fragment {
    private FragmentWeeklyChallengeBBinding binding;
    private ExerciseRoutineViewModel viewModel;
    private SharedViewModel sharedViewModel;
    private ArrayList<Round> listRound = new ArrayList<>();
    private RoundRCVAdapter roundAdapter;
    private String level;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_weekly_challenge_b, container, false);
        viewModel=new ViewModelProvider(requireActivity()).get(ExerciseRoutineViewModel.class);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        level = UserData.getUserLevel(getContext());
        return binding.getRoot();
    }
    private void loadData()
    {
        sharedViewModel.getSelected().observe(getViewLifecycleOwner(), new Observer<Workout>() {
            @Override
            public void onChanged(Workout workout) {
                Glide.with(binding.imgBanner.getContext())
                        .load(workout.getThumbnail())
                        .placeholder(R.drawable.woman_helping_man_gym)
                        .error(R.drawable.woman_helping_man_gym)
                        .into(binding.imgBanner);
                binding.totalTime.setText(workout.getTotalTime() + " Minutes");
                binding.kcal.setText(workout.getKcal() + " Kcal");
                binding.level.setText(workout.getExerciseCount() + " Exercises");
                for (Round round : workout.getRound())
                    listRound.add(round);
            }
        });

        roundAdapter = new RoundRCVAdapter(listRound);
        binding.rcvRound.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rcvRound.setAdapter(roundAdapter);
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
