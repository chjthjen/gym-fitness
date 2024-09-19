package com.example.gymfitness.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gymfitness.data.DAO.ProgressTrackingDAO;
import com.example.gymfitness.data.DayOverview;
import com.example.gymfitness.data.database.FitnessDB;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProgressTrackingViewModel extends AndroidViewModel {
    private final MutableLiveData<List<DayOverview>> data=new MutableLiveData<>();
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    ProgressTrackingDAO progressTrackingDAO;


    public ProgressTrackingViewModel(@NonNull Application application) {
        super(application);
        FitnessDB db = FitnessDB.getInstance(application.getApplicationContext());
        progressTrackingDAO = db.progressTrackingDAO();

        executorService.execute(()->{
            List<DayOverview> dayOverviews = progressTrackingDAO.getDayOverview();
            data.postValue(dayOverviews);
        });

    }

    public void loadProgressDayOverView(){
        executorService.execute(()->{
            List<DayOverview> dayOverviews = progressTrackingDAO.getDayOverview();
            Log.d("ViewModel", "Data fetched: " + dayOverviews.get(0).getDuration());
            data.postValue(dayOverviews);
        });
    }
    public MutableLiveData<List<DayOverview>> getData() {
        loadProgressDayOverView();
        return data;
    }

    // lay reps theo tung thang
    public Map<String,Integer> calculateTotalsByMonth(List<DayOverview> dayOverviews){
        Map<String,Integer> totalsByMonth=new HashMap<>();
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (DayOverview dayOverview:dayOverviews){
            LocalDate localDate=LocalDate.parse(dayOverview.getDate(),formatter);
            String yearMonth=localDate.getYear()+"-"+ String.format("%02d", localDate.getMonthValue());

            int rep=dayOverview.getRep();
            totalsByMonth.putIfAbsent(yearMonth,0);
            totalsByMonth.put(yearMonth,totalsByMonth.getOrDefault(yearMonth,0)+rep);
        }
        return totalsByMonth;

    }

    public List<Integer> getMonths(List<DayOverview> dayOverviews) {
        List<Integer> months = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (DayOverview dayOverview : dayOverviews) {
            if (dayOverview.getDate() != null) {
                try {
                    LocalDate localDate = LocalDate.parse(dayOverview.getDate(), formatter);
                    int month = localDate.getMonthValue();
                    if (!months.contains(month)) {
                        months.add(month);
                    }
                } catch (DateTimeParseException e) {
                    Log.e("ProgressTrackingViewModel", "Date parsing error: " + e.getMessage());
                }
            } else {
                Log.e("ProgressTrackingViewModel", "Date string is null");
            }
        }
        return months;
    }

}
