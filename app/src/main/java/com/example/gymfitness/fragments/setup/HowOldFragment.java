package com.example.gymfitness.fragments.setup;


import android.graphics.Typeface;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gymfitness.R;
import com.example.gymfitness.databinding.FragmentHowOldBinding;
import com.shawnlin.numberpicker.NumberPicker;


public class HowOldFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private FragmentHowOldBinding binding;

    public HowOldFragment() {

    }

    public static HowOldFragment newInstance(String param1, String param2) {
        HowOldFragment fragment = new HowOldFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_how_old,container,false);
        binding.numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                binding.numPicker.setText(String.valueOf(newVal));
            }
        });
        binding.numberPicker.setSelectedTypeface(getString(R.string.number_picker_formatter), Typeface.BOLD);
        binding.numberPicker.setTypeface(getString(R.string.number_picker_formatter), Typeface.BOLD);

        return binding.getRoot();
    }
}