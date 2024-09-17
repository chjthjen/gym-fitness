package com.example.gymfitness.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.gymfitness.data.DAO.RoutineRoundDAO;
import com.example.gymfitness.data.entities.Exercise;
import com.example.gymfitness.data.database.FitnessDB;
import com.example.gymfitness.data.entities.RoutineRound;
import com.example.gymfitness.data.entities.RoutineRoundExerciseCrossRef;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OwnRoutineViewModel extends AndroidViewModel {
    private LiveData<List<RoutineRound>> routineRoundsLiveData;
    private RoutineRoundDAO routineRoundDAO;
    private final ExecutorService executorService;

    private MutableLiveData<List<Exercise>> exeriseList = new MutableLiveData<>();

    public OwnRoutineViewModel(Application application) {
        super(application);
        FitnessDB db = FitnessDB.getInstance(application);
        routineRoundDAO = db.routineRoundDAO();
        routineRoundsLiveData = routineRoundDAO.getAllRoutineRounds();
        executorService = Executors.newFixedThreadPool(2);
    }

    public LiveData<List<RoutineRound>> getRoutineRoundsLiveData() {
        return routineRoundsLiveData;
    }

    // Thêm Round vào Routine
    public void addRoutineRound(String roundName) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                RoutineRound newRoutineRound = new RoutineRound(roundName);
                routineRoundDAO.insert(newRoutineRound);
            }
        });
    }

    // Xóa Round và cập nhật lại danh sách Round
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

    public void getExercisesFromFirebase()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = database.getReference().child("Workout");

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Exercise> exercises = new ArrayList<>();

                for(DataSnapshot workoutSnapshot : snapshot.getChildren()) {
                    DataSnapshot roundSnapshot = workoutSnapshot.child("round");

                    for(DataSnapshot round : roundSnapshot.getChildren()) {
                        for (DataSnapshot exerciseSnapshot : round.getChildren()) {
                            Exercise exercise = exerciseSnapshot.getValue(Exercise.class);
                            String exerciseName = exerciseSnapshot.getKey();
                            if(exercise != null) {
                                exercise.setExercise_name(exerciseName);
                                exercises.add(exercise);

                                // Lưu vào Room Database
                                executorService.execute(() -> {
                                    Exercise existingExercise = routineRoundDAO.getExerciseByName(exerciseName);
                                    if (existingExercise == null) {
                                        routineRoundDAO.insertExercise(exercise);
                                    }
                                });
                            }
                        }
                    }
                }
                exeriseList.postValue(exercises);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addExerciseToRound(Exercise exercise, int roundId) {
        executorService.execute(() -> {
            // Tìm RoutineRound theo roundId
            RoutineRound routineRound = routineRoundDAO.getRoutineRoundById(roundId);
            if (routineRound != null) {
                // Kiểm tra xem Exercise đã tồn tại hay chưa
                Exercise existingExercise = routineRoundDAO.getExerciseByName(exercise.getExercise_name());
                int exerciseId;

                if (existingExercise == null) {
                    // Nếu Exercise chưa tồn tại, thêm nó vào cơ sở dữ liệu
                    exerciseId = (int) routineRoundDAO.insertExercise(exercise);
                } else {
                    // Nếu Exercise đã tồn tại, lấy exerciseId
                    exerciseId = existingExercise.getExercise_id();
                }

                // Tạo liên kết giữa RoutineRound và Exercise bằng RoutineRoundExerciseCrossRef
                RoutineRoundExerciseCrossRef crossRef = new RoutineRoundExerciseCrossRef(routineRound.getId(), exerciseId);
                routineRoundDAO.insertRoutineRoundExerciseCrossRef(crossRef);
            }
        });
    }

    // Lấy dữ liệu Exercises từ Firebase
    public LiveData<List<Exercise>> getExercises() {
        return exeriseList;
    }

    public LiveData<List<Exercise>> getExercisesForRound(int roundId) {
        return routineRoundDAO.getExercisesForRoutineRound(roundId);
    }

}
