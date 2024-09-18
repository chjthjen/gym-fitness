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

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProgressTrackingViewModel extends AndroidViewModel {
    private final MutableLiveData<List<DayOverview>> data=new MutableLiveData<>();
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    ProgressTrackingDAO progressTrackingDAO;


    public ProgressTrackingViewModel(@NonNull Application application) {
        super(application);
        FitnessDB db = FitnessDB.getInstance(application);
        progressTrackingDAO = db.progressTrackingDAO();

        executorService.execute(()->{
            data.postValue(progressTrackingDAO.getDayOverview());
        });
//        FitnessDB db=FitnessDB.getInstance(application);
//        data.postValue(progressTrackingDAO.getDayOverview());
    }

    public MutableLiveData<List<DayOverview>> getData() {
        return data;
    }
}
