package com.example.gymfitness.fragments.setup;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gymfitness.R;
import com.example.gymfitness.databinding.FragmentGenderBinding;


public class GenderFragment extends Fragment {
    FragmentGenderBinding binding;
    private static int sex = 0;
    public GenderFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gender, container, false);
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
            }
        });
    }
}