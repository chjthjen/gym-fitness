package com.example.gymfitness.fragments.resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import com.example.gymfitness.R;
import com.example.gymfitness.databinding.FragmentExerciseRoutineBinding;

public class RoundExerciseResourceFragment extends Fragment {
    private FragmentExerciseRoutineBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_exercise_routine,container,false);


        return binding.getRoot();
    }
}
