package com.example.gymfitness.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.gymfitness.R;
import com.example.gymfitness.adapters.CalendarAdapter;
import com.example.gymfitness.databinding.FragmentWorkoutLogBinding;
import com.example.gymfitness.viewmodels.WorkoutLogViewModel;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class WorkoutLogFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FragmentWorkoutLogBinding binding;
    private CalendarAdapter calendarAdapter;
    private WorkoutLogViewModel viewModel;

    public WorkoutLogFragment() {
        // Required empty public constructor
    }

    public static WorkoutLogFragment newInstance(String param1, String param2) {
        WorkoutLogFragment fragment = new WorkoutLogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWorkoutLogBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(WorkoutLogViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<String> months = List.of("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.style_spinner, months);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spnMonth.setAdapter(arrayAdapter);

        viewModel.getMonth().observe(getViewLifecycleOwner(), month -> {
            binding.spnMonth.setSelection(month - 1);
        });

        viewModel.getDaysOfMonth().observe(getViewLifecycleOwner(), daysOfMonth -> {
            if (daysOfMonth != null) {
                int currentDay = LocalDate.now().getDayOfMonth();
                int currentMonth = LocalDate.now().getMonthValue();
                int selectedPosition = -1;

                // Nếu ngày nằm trong tháng hiện tại
                if (viewModel.getMonth().getValue() == currentMonth) {
                    selectedPosition = daysOfMonth.indexOf(String.valueOf(currentDay));
                }

                // Nếu không thuộc tháng hiện tại chọn ngày 1
                if (selectedPosition == -1) {
                    selectedPosition = daysOfMonth.indexOf("1");
                    if (selectedPosition == -1) {
                        selectedPosition = 0;
                    }
                }

                calendarAdapter = new CalendarAdapter(daysOfMonth, selectedPosition);

                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 7);
                binding.calendarRecyclerView.setLayoutManager(gridLayoutManager);
                binding.calendarRecyclerView.setAdapter(calendarAdapter);
            }
        });


        binding.spnMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.setMonth(position + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}