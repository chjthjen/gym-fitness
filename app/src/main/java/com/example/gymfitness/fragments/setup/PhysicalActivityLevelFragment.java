package com.example.gymfitness.fragments.setup;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gymfitness.R;
import com.example.gymfitness.databinding.FragmentPhysicalActivityLevelBinding;
import com.example.gymfitness.viewmodels.SetUpViewModel;


public class PhysicalActivityLevelFragment extends Fragment {
    private FragmentPhysicalActivityLevelBinding binding;
    private static int level_is = 1;
    private SetUpViewModel setUpViewModel;
    private ColorStateList colorStateList;
    public PhysicalActivityLevelFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_physical_activity_level,container,false);
        setUpViewModel = new ViewModelProvider(requireActivity()).get(SetUpViewModel.class);
        setUpViewModel.setLevel("beginner");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnBeginner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level_is = 0;
                selecteGool(level_is);
                setUpViewModel.setLevel("beginner");
            }
        });
        binding.btnIntermediate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level_is = 1;
                selecteGool(level_is);
                setUpViewModel.setLevel("intermediate");
            }
        });
        binding.btnAdvanced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level_is = 2;
                selecteGool(level_is);
                setUpViewModel.setLevel("advance");
            }
        });

    }

    private void selecteGool(int level_is)
    {
        if(level_is == 0)
        {
            colorStateList = ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.limegreen));
            binding.btnBeginner.setBackgroundTintList(colorStateList);
            binding.btnBeginner.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            colorStateList = ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.white));
            binding.btnIntermediate.setBackgroundTintList(colorStateList);
            binding.btnIntermediate.setTextColor(ContextCompat.getColor(getContext(), R.color.purple));
            binding.btnAdvanced.setBackgroundTintList(colorStateList);
            binding.btnAdvanced.setTextColor(ContextCompat.getColor(getContext(), R.color.purple));
        }
        else if(level_is == 1)
        {
            colorStateList = ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.limegreen));
            binding.btnIntermediate.setBackgroundTintList(colorStateList);
            binding.btnIntermediate.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            colorStateList = ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.white));
            binding.btnBeginner.setBackgroundTintList(colorStateList);
            binding.btnBeginner.setTextColor(ContextCompat.getColor(getContext(), R.color.purple));
            binding.btnAdvanced.setBackgroundTintList(colorStateList);
            binding.btnAdvanced.setTextColor(ContextCompat.getColor(getContext(), R.color.purple));
        }
        else
        {
            colorStateList = ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.limegreen));
            binding.btnAdvanced.setBackgroundTintList(colorStateList);
            binding.btnAdvanced.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            colorStateList = ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.white));
            binding.btnBeginner.setBackgroundTintList(colorStateList);
            binding.btnBeginner.setTextColor(ContextCompat.getColor(getContext(), R.color.purple));
            binding.btnIntermediate.setBackgroundTintList(colorStateList);
            binding.btnIntermediate.setTextColor(ContextCompat.getColor(getContext(), R.color.purple));
        }
    }
}