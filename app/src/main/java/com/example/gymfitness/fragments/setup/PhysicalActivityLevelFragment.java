package com.example.gymfitness.fragments.setup;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gymfitness.R;
import com.example.gymfitness.databinding.FragmentPhysicalActivityLevelBinding;


public class PhysicalActivityLevelFragment extends Fragment {
    FragmentPhysicalActivityLevelBinding binding;
    private static int gool_is = 1;
    private ColorStateList colorStateList;
    public PhysicalActivityLevelFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_physical_activity_level,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnBeginner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gool_is = 0;
                selecteGool(gool_is);
            }
        });
        binding.btnIntermediate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gool_is = 1;
                selecteGool(gool_is);
            }
        });
        binding.btnAdvanced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gool_is = 2;
                selecteGool(gool_is);
            }
        });

    }

    private void selecteGool(int gool_is)
    {
        if(gool_is == 0)
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
        else if(gool_is == 1)
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