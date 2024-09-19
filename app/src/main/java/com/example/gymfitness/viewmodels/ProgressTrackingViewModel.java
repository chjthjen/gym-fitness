package com.example.gymfitness.viewmodels;

import android.app.Application;

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
            data.postValue(progressTrackingDAO.getDayOverview());
        });

    }

    public MutableLiveData<List<DayOverview>> getData() {
        return data;
    }

    // lay reps theo tung thang
    public Map<String,Integer> calculateTotalsByMonth(List<DayOverview> dayOverviews){
        Map<String,Integer> totalsByMonth=new HashMap<>();
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (DayOverview dayOverview:dayOverviews){
            LocalDate localDate=LocalDate.parse(dayOverview.getTime(),formatter);
            String yearMonth=localDate.getYear()+"-"+ String.format("%02d", localDate.getMonthValue());

            int rep=Integer.parseInt(dayOverview.getReps());
            totalsByMonth.putIfAbsent(yearMonth,0);
            totalsByMonth.put(yearMonth,totalsByMonth.getOrDefault(yearMonth,0)+rep);
        }
        return totalsByMonth;

    }

    public List<Integer> getMonths(List<DayOverview> dayOverviews){
        List<Integer> months=new ArrayList<>();
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (DayOverview dayOverview:dayOverviews){
            LocalDate localDate=LocalDate.parse(dayOverview.getTime(),formatter);
            int month=localDate.getMonthValue();
            if(months.contains(month)){
                months.add(month);
            }

        }
        return months;
    }
}
