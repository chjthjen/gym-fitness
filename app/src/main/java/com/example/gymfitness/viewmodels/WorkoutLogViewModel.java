package com.example.gymfitness.viewmodels;

import static java.security.AccessController.getContext;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gymfitness.data.DAO.WorkoutLogDAO;
import com.example.gymfitness.data.database.FitnessDB;
import com.example.gymfitness.data.entities.WorkoutLog;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;

public class WorkoutLogViewModel extends ViewModel {
    private final MutableLiveData<Integer> monthLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> yearLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<String>> daysOfMonthLiveData = new MutableLiveData<>();

    public WorkoutLogViewModel() {
        YearMonth currentYearMonth = YearMonth.now();
        monthLiveData.setValue(currentYearMonth.getMonthValue());
        yearLiveData.setValue(currentYearMonth.getYear());
        updateCalendar();
    }

    public LiveData<Integer> getMonth() {
        return monthLiveData;
    }

    public LiveData<Integer> getYear() {
        return yearLiveData;
    }

    public LiveData<List<String>> getDaysOfMonth() {
        return daysOfMonthLiveData;
    }

    public void setMonth(int month) {
        monthLiveData.setValue(month);
        updateCalendar();
    }

    public void setYear(int year) {
        yearLiveData.setValue(year);
        updateCalendar();
    }

    private void updateCalendar() {
        Integer month = monthLiveData.getValue();
        Integer year = yearLiveData.getValue();
        if (month != null && year != null) {
            List<String> days = getDaysOfMonth(year, month);
            daysOfMonthLiveData.setValue(days);
        }
    }

    private List<String> getDaysOfMonth(int year, int month) {
        List<String> days = new ArrayList<>();
        YearMonth yearMonth = YearMonth.of(year, month);
        int daysInMonth = yearMonth.lengthOfMonth();
        int dayOfWeek = yearMonth.atDay(1).getDayOfWeek().getValue();

        for (int i = 1; i < dayOfWeek; i++) {
            days.add("");
        }

        for (int day = 1; day <= daysInMonth; day++) {
            days.add(String.valueOf(day));
        }

        return days;
    }


}
