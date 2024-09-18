package com.example.gymfitness.fragments.challenge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.gymfitness.R;
import com.example.gymfitness.activities.HomeActivity;
import com.example.gymfitness.data.entities.Workout;
import com.example.gymfitness.databinding.FragmentCongratulationBinding;
import com.example.gymfitness.viewmodels.SharedViewModel;

import java.util.Objects;

public class CongratulationFragment extends Fragment {

    FragmentCongratulationBinding binding;
    private SharedViewModel sharedViewModel;
    private NavController navController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_congratulation,container,false);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);
        Workout selectedValue = sharedViewModel.getSelected().getValue();
        binding.hours.setText(selectedValue.getTotalTime() + " hours");
        binding.calorie.setText(selectedValue.getKcal() + " calorie");
        binding.moderate.setText(selectedValue.getLevel());

        binding.btnGoToNextWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_congratulationFragment_to_communityFragment2);
                Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
                ((HomeActivity) requireActivity()).getBottomNavigationView().setVisibility(View.VISIBLE);
            }
        });

        binding.btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_congratulationFragment_to_homeFragment);
                Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
                ((HomeActivity) requireActivity()).getBottomNavigationView().setVisibility(View.VISIBLE);
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
        ((HomeActivity) requireActivity()).getBottomNavigationView().setVisibility(View.GONE);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
        ((HomeActivity) requireActivity()).getBottomNavigationView().setVisibility(View.VISIBLE);
    }
}
