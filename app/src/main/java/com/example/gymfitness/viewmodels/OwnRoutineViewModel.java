package com.example.gymfitness.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gymfitness.R;
import com.example.gymfitness.data.DAO.RoutineRoundDAO;
import com.example.gymfitness.data.database.FitnessDB;
import com.example.gymfitness.data.entities.RoutineRound;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OwnRoutineViewModel extends AndroidViewModel {
    private LiveData<List<RoutineRound>> routineRounds;
    private RoutineRoundDAO routineRoundDAO;
    private final ExecutorService executorService;

    public OwnRoutineViewModel(Application application) {
        super(application);
        FitnessDB db = FitnessDB.getInstance(application);
        routineRoundDAO = db.routineRoundDAO();
        routineRounds = routineRoundDAO.getAllRoutineRounds();
        executorService = Executors.newFixedThreadPool(2);
    }

    public LiveData<List<RoutineRound>> getRoutineRounds() {
        return routineRounds;
    }

    public void addRoutineRound(String roundName) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                RoutineRound newRoutineRound = new RoutineRound(roundName);
                routineRoundDAO.insert(newRoutineRound);
            }
        });
    }

    public void removeRoutineRound(int roundId) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                RoutineRound roundToRemove = new RoutineRound();
                roundToRemove.setId(roundId);
                routineRoundDAO.delete(roundToRemove);

                // Sau khi xóa, cập nhật lại thứ tự các round còn lại
                List<RoutineRound> remainingRounds = routineRoundDAO.getAllRoutineRoundsDirect();
                if(remainingRounds != null) {
                    for (int i = 0; i < remainingRounds.size(); i++) {
                        String newRoundName = "Round " + (i + 1); // Cập nhật tên theo thứ tự mới
                        RoutineRound routineRound = remainingRounds.get(i);
                        routineRound.setName(newRoundName); // Cập nhật tên round
                        routineRoundDAO.update(routineRound);
                    }
                }
            }
        });
    }
}
