package com.example.gymfitness.fragments.setup;


import android.graphics.Typeface;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gymfitness.R;
import com.example.gymfitness.databinding.FragmentHowOldBinding;
import com.example.gymfitness.viewmodels.SetUpViewModel;
import com.shawnlin.numberpicker.NumberPicker;


public class HowOldFragment extends Fragment {

    FragmentHowOldBinding binding;
    SetUpViewModel setUpViewModel;

    public HowOldFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_how_old,container,false);
        setUpViewModel = new ViewModelProvider(requireActivity()).get(SetUpViewModel.class);
        setUpViewModel.setAge(binding.numberPicker.getValue());
        binding.numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                binding.numPicker.setText(String.valueOf(newVal));
                setUpViewModel.setAge(newVal);
            }
        });
        binding.numberPicker.setSelectedTypeface(getString(R.string.number_picker_formatter), Typeface.BOLD);
        binding.numberPicker.setTypeface(getString(R.string.number_picker_formatter), Typeface.BOLD);

        return binding.getRoot();
    }
}