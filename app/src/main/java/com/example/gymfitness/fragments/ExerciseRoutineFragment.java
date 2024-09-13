package com.example.gymfitness.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gymfitness.R;
import com.example.gymfitness.adapters.ExerciseAdapter;
import com.example.gymfitness.data.Exercise;
import com.example.gymfitness.databinding.FragmentExerciseRoutineBinding;
import com.example.gymfitness.helpers.SpacesItemDecoration;
import com.example.gymfitness.viewmodels.ExerciseRoutineViewModel;

import java.util.ArrayList;
import java.util.List;


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
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_exercise_routine, container, false);
        View view=binding.getRoot();
        viewModel=new ViewModelProvider(this).get(ExerciseRoutineViewModel.class);


        List<Exercise> exercises1=new ArrayList<>();
        List<Exercise> exercises2=new ArrayList<>();
        exercises1.add(new Exercise(R.drawable.ic_round,"Kettlebell swing","00:30","repetition 3x"));
        exercises1.add(new Exercise(R.drawable.ic_round,"Kettlebell swing","00:30","repetition 3x"));
        exercises1.add(new Exercise(R.drawable.ic_round,"Kettlebell swing","00:30","repetition 3x"));
        exercises1.add(new Exercise(R.drawable.ic_round,"Kettlebell swing","00:30","repetition 3x"));
        exercises2.add(new Exercise(R.drawable.ic_round,"Kettlebell swing","00:30","repetition 3x"));
        exercises2.add(new Exercise(R.drawable.ic_round,"Kettlebell swing","00:30","repetition 3x"));

        ExerciseAdapter adapter1=new ExerciseAdapter(exercises1);
        ExerciseAdapter adapter2=new ExerciseAdapter(exercises2);
        binding.rvRound1.setAdapter(adapter1);
        binding.rvRound1.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));
        binding.rvRound1.addItemDecoration(new SpacesItemDecoration(16));
        binding.rvRound2.setAdapter(adapter2);
        binding.rvRound2.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));
        binding.rvRound2.addItemDecoration(new SpacesItemDecoration(16));
        return view;
    }
}