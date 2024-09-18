package com.example.gymfitness.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.gymfitness.R;
import com.example.gymfitness.adapters.CalendarAdapter;
import com.example.gymfitness.adapters.WorkoutLogAdapter;
import com.example.gymfitness.data.DAO.WorkoutLogDAO;
import com.example.gymfitness.data.database.FitnessDB;
import com.example.gymfitness.data.entities.WorkoutLog;
import com.example.gymfitness.databinding.FragmentWorkoutLogBinding;
import com.example.gymfitness.viewmodels.WorkoutLogViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WorkoutLogFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FragmentWorkoutLogBinding binding;
    private WorkoutLogViewModel viewModel;
    private WorkoutLogDAO workoutLogDAO;
    static ExecutorService executorService;
    private WorkoutLogAdapter workoutLogAdapter;
    private CalendarAdapter calendarAdapter;
    private LiveData<List<WorkoutLog>> workoutLogsLiveData;

    public WorkoutLogFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWorkoutLogBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(WorkoutLogViewModel.class);
        workoutLogDAO = FitnessDB.getInstance(getContext()).workoutLogDAO();
        executorService = Executors.newSingleThreadExecutor();
        workoutLogsLiveData = new MutableLiveData<>();
        workoutLogAdapter = new WorkoutLogAdapter(new ArrayList<>());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<String> months = List.of("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.style_spinner, months);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spnMonth.setAdapter(arrayAdapter);


        binding.listActi.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.listActi.setAdapter(workoutLogAdapter); // Gán adapter cho RecyclerView

        viewModel.getMonth().observe(getViewLifecycleOwner(), month -> {
            binding.spnMonth.setSelection(month - 1);
        });

        workoutLogsLiveData.observe(getViewLifecycleOwner(), logs -> {
            if(logs != null) {
                workoutLogAdapter.updateData(logs);
            }
        });

        binding.calendarRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 7));

        viewModel.getDaysOfMonth().observe(getViewLifecycleOwner(), daysOfMonth -> {
            if (daysOfMonth != null) {
                int currentDay = LocalDate.now().getDayOfMonth();
                int currentMonth = LocalDate.now().getMonthValue();
                int selectedPosition = -1;

                if (viewModel.getMonth().getValue() == currentMonth) {
                    selectedPosition = daysOfMonth.indexOf(String.valueOf(currentDay));
                }

                if (selectedPosition == -1) {
                    selectedPosition = daysOfMonth.indexOf("1");
                    if (selectedPosition == -1) {
                        selectedPosition = 0;
                    }
                }
                int finalSelectedPosition = selectedPosition;
                executorService.execute(() -> {
                    List<Date> specialDates = workoutLogDAO.getAllDates();
                    List<String> specialDays = new ArrayList<>();
                    SimpleDateFormat dayFormat = new SimpleDateFormat("d", Locale.getDefault());

                    for (Date date : specialDates) {
                        specialDays.add(dayFormat.format(date));
                    }

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    LocalDate currentDate = LocalDate.now();
                    String dateString = currentDate.getYear() + "-" + currentMonth + "-" + currentDate.getDayOfMonth();
                    Date selectedDate = null;
                    try {
                        selectedDate = dateFormat.parse(dateString);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

                    List<WorkoutLog> logsForSelectedDay = workoutLogDAO.getWorkoutLogByDate(selectedDate);
                        getActivity().runOnUiThread(() -> {
                            calendarAdapter = new CalendarAdapter(daysOfMonth, finalSelectedPosition, specialDays, binding.spnMonth.getSelectedItem().toString(), this::onDaySelected);
                            binding.calendarRecyclerView.setAdapter(calendarAdapter);


                            workoutLogAdapter = new WorkoutLogAdapter(logsForSelectedDay);
                            binding.listActi.setLayoutManager(new LinearLayoutManager(getContext()));
                            binding.listActi.setAdapter(workoutLogAdapter);
                        });

                });
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

    private void onDaySelected(String dayString, String month) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        try {
            LocalDate currentDate = LocalDate.now();
            String dateString = currentDate.getYear() + "-" + month + "-" + dayString;
            Date selectedDate = dateFormat.parse(dateString);

            executorService.execute(() -> {
                List<WorkoutLog> logsForSelectedDay = workoutLogDAO.getWorkoutLogByDate(selectedDate);
                Log.e("hi", logsForSelectedDay.toString());

                getActivity().runOnUiThread(() -> {
                    workoutLogAdapter = new WorkoutLogAdapter(logsForSelectedDay);
                    binding.listActi.setLayoutManager(new LinearLayoutManager(getContext()));
                    binding.listActi.setAdapter(workoutLogAdapter);
                });
            });
        } catch (ParseException e) {
            Log.e("onDaySelected", "Lỗi khi phân tích ngày: " + e.getMessage());
        }
    }

    private static @Nullable Date getParse(SimpleDateFormat dateFormat, String dateString) throws ParseException {
        return dateFormat.parse(dateString);
    }

}