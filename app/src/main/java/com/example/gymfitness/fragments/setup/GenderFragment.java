package com.example.gymfitness.fragments.setup;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gymfitness.R;
import com.example.gymfitness.databinding.FragmentGenderBinding;
import com.example.gymfitness.viewmodels.SetUpViewModel;


public class GenderFragment extends Fragment {
    FragmentGenderBinding binding;
    SetUpViewModel setUpViewModel;
    private int sex = 0;
    public GenderFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gender, container, false);
        setUpViewModel = new ViewModelProvider(requireActivity()).get(SetUpViewModel.class);
        // set default gender
        setUpViewModel.setGender("Male");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sex == 0)
                    return;
                sex = 0;
                binding.btnMale.setImageResource(R.drawable.male_on);
                binding.btnFemale.setImageResource(R.drawable.fmale_off);
                setUpViewModel.setGender("Male");
            }
        });
        binding.btnFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sex == 1)
                    return;
                sex = 1;
                binding.btnFemale.setImageResource(R.drawable.female_on);
                binding.btnMale.setImageResource(R.drawable.male_off);
                setUpViewModel.setGender("Female");
            }
        });
    }
}