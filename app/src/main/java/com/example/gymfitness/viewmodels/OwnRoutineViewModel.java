package com.example.gymfitness.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.gymfitness.data.DAO.RoutineRoundDAO;
import com.example.gymfitness.data.DAO.RoutineRoundExerciseRefDAO;
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
    private final LiveData<List<RoutineRound>> routineRoundsLiveData;
    private final RoutineRoundDAO routineRoundDAO;
    private final RoutineRoundExerciseRefDAO routineRoundExerciseRefDAO;
    private final ExecutorService executorService;
    private final MutableLiveData<List<Exercise>> exeriseList = new MutableLiveData<>();
    private final MutableLiveData<Boolean> exerciseAddedStatus = new MutableLiveData<>();

    public OwnRoutineViewModel(Application application) {
        super(application);
        FitnessDB db = FitnessDB.getInstance(application);
        routineRoundDAO = db.routineRoundDAO();
        routineRoundExerciseRefDAO = db.routineRoundExerciseRefDAO();
        routineRoundsLiveData = routineRoundDAO.getAllRoutineRounds();
        executorService = Executors.newFixedThreadPool(2);
    }

    public LiveData<List<RoutineRound>> getRoutineRoundsLiveData() {
        return routineRoundsLiveData;
    }

    public void addRoutineRound(String roundName) {
        executorService.execute(() -> {
            RoutineRound newRoutineRound = new RoutineRound(roundName);
            routineRoundDAO.insert(newRoutineRound);
        });
    }

    public void removeRoutineRound(int roundId) {
        executorService.execute(() -> {
            RoutineRound roundToRemove = new RoutineRound();
            roundToRemove.setId(roundId);
            routineRoundDAO.delete(roundToRemove);

            List<RoutineRound> remainingRounds = routineRoundDAO.getAllRoutineRoundsDirect();
            if (remainingRounds != null) {
                for (int i = 0; i < remainingRounds.size(); i++) {
                    String newRoundName = "Round " + (i + 1);
                    RoutineRound routineRound = remainingRounds.get(i);
                    routineRound.setName(newRoundName);
                    routineRoundDAO.update(routineRound);
                }
            }
        });
    }

    public void getExercisesFromFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = database.getReference().child("Workout");
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                executorService.execute(() -> {
                    List<Exercise> exercises = new ArrayList<>();
                    for (DataSnapshot workoutSnapshot : snapshot.getChildren()) {
                        DataSnapshot roundSnapshot = workoutSnapshot.child("round");
                        for (DataSnapshot round : roundSnapshot.getChildren()) {
                            for (DataSnapshot exerciseSnapshot : round.getChildren()) {
                                Exercise exercise = exerciseSnapshot.getValue(Exercise.class);
                                String exerciseName = exerciseSnapshot.getKey();
                                if (exercise != null) {
                                    exercise.setExercise_name(exerciseName);
                                    Exercise existingExercise = routineRoundDAO.getExerciseByName(exerciseName);
                                    if (existingExercise == null) {
                                        long newId = routineRoundDAO.insertExercise(exercise);
                                        exercise.setExercise_id((int) newId);
                                    } else {
                                        exercise.setExercise_id(existingExercise.getExercise_id());
                                    }
                                    exercises.add(exercise);
                                }
                            }
                        }
                    }
                    exeriseList.postValue(exercises);
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void addExerciseToRound(Exercise exercise, int roundId) {
        executorService.execute(() -> {
            RoutineRound routineRound = routineRoundDAO.getRoutineRoundById(roundId);
            if (routineRound != null) {
                Exercise existingExercise = routineRoundDAO.getExerciseByName(exercise.getExercise_name());
                int exerciseId;

                if (existingExercise == null) {
                    exerciseId = (int) routineRoundDAO.insertExercise(exercise);
                } else {
                    exerciseId = existingExercise.getExercise_id();
                }

                RoutineRoundExerciseCrossRef crossRef = new RoutineRoundExerciseCrossRef(routineRound.getId(), exerciseId);
                routineRoundDAO.insertRoutineRoundExerciseCrossRef(crossRef);

                // Cập nhật trạng thái thêm bài tập thành công
                exerciseAddedStatus.postValue(true);
            } else {
                exerciseAddedStatus.postValue(false);
            }
        });
    }

    public LiveData<List<Exercise>> getExercises() {
        return exeriseList;
    }

    public LiveData<List<Exercise>> getExercisesForRound(int roundId) {
        return routineRoundDAO.getExercisesForRoutineRound(roundId);
    }

    public void removeExerciseFromRoutineRound(Exercise exercise, int roundId) {
        executorService.execute(() -> {
            Exercise existingExercise = routineRoundDAO.getExerciseByName(exercise.getExercise_name());
            if (existingExercise != null) {
                int exerciseId = existingExercise.getExercise_id();
                routineRoundExerciseRefDAO.deleteRoutineRoundExerciseCrossRef(roundId, exerciseId);
            } else {}
        });
    }
}
