package com.example.gymfitness.fragments.setup;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.gymfitness.R;
import com.example.gymfitness.databinding.FragmentHowOldBinding;
import com.example.gymfitness.databinding.FragmentWeightBinding;
import com.example.gymfitness.utils.WeightConverter;
import com.example.gymfitness.viewmodels.SetUpViewModel;
import com.shawnlin.numberpicker.NumberPicker;

public class WeightFragment extends Fragment {

    private FragmentWeightBinding binding;
    private SetUpViewModel setUpViewModel;
    private String measurement = "Kg";
    private float weight;
    public WeightFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_weight, container, false);
        setUpViewModel = new ViewModelProvider(requireActivity()).get(SetUpViewModel.class);
        binding.np.setSelectedTypeface(getString(R.string.number_picker_formatter), Typeface.BOLD);
        binding.np.setTypeface(getString(R.string.number_picker_formatter), Typeface.BOLD);
        setUpViewModel.setWeight(75);


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.txtKg.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                binding.txtMeasurementUnit.setText("Kg");
                measurement = "Kg";
                setUpViewModel.setWeight(binding.np.getValue());
            }
        });
        binding.txtLb.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                binding.txtMeasurementUnit.setText("Lb");
                measurement = "Lb";
                setUpViewModel.setWeight(WeightConverter.lbToKg(binding.np.getValue()));

            }
        });


        binding.np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                binding.txtWeight.setText(String.valueOf(newVal));
                weight = (float) newVal;
                if(measurement == "Lb")
                    setUpViewModel.setWeight(WeightConverter.lbToKg(weight));
                else
                    setUpViewModel.setWeight((float) weight);
            }
        });

        binding.np.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    binding.ruler.setAlpha(0.9f);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    binding.ruler.setAlpha(1.0f);
                    break;
            }
            return false;
        });
    }
}