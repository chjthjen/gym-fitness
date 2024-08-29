package com.example.gymfitness.fragments.setup;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gymfitness.R;
import com.example.gymfitness.databinding.FragmentHeightBinding;

public class HeightFragment extends Fragment {
    FragmentHeightBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_height, container, false);
        binding = FragmentHeightBinding.bind(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.numberPicker.setMaxValue(250);
        binding.numberPicker.setMinValue(100);
        binding.numberPicker.setValue(170);
    }
}