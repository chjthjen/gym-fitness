package com.example.gymfitness.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.gymfitness.data.entities.Round;
import com.example.gymfitness.data.entities.Workout;
import com.example.gymfitness.databinding.FragmentExerciseRoutineBinding;
import com.example.gymfitness.viewmodels.ExerciseRoutineViewModel;
import com.example.gymfitness.viewmodels.SharedViewModel;

import java.util.ArrayList;


public class ExerciseRoutineFragment extends Fragment {

    private FragmentExerciseRoutineBinding binding;
    private ExerciseRoutineViewModel viewModel;
    private SharedViewModel sharedViewModel;
    private Workout getWorkout;
    private ArrayList<Round> listRound = new ArrayList<>();
    private RoundRCVAdapter roundAdapter;
    public ExerciseRoutineFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_exercise_routine, container, false);
        viewModel=new ViewModelProvider(requireActivity()).get(ExerciseRoutineViewModel.class);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModel.getSelected().observe(getViewLifecycleOwner(), new Observer<Workout>() {
            @Override
            public void onChanged(Workout workout) {
                getWorkout = workout;
                Glide.with(binding.imgBanner.getContext())
                        .load(workout.getThumbnail())
                        .placeholder(R.drawable.woman_helping_man_gym)
                        .error(R.drawable.woman_helping_man_gym)
                        .into(binding.imgBanner);
                binding.totalTime.setText(getWorkout.getTotalTime() + " Minutes");
                binding.kcal.setText(getWorkout.getKcal() + " Kcal");
                binding.level.setText(getWorkout.getExerciseCount() + " Exercises");
                for (Round round : workout.getRound())
                {
                    Log.d("ditconmem", round.getRound_name());
                    listRound.add(round);
                }
            }
        });


        roundAdapter = new RoundRCVAdapter(listRound);

        binding.rcvRound.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rcvRound.setAdapter(roundAdapter);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
//        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Workout");
    }
}