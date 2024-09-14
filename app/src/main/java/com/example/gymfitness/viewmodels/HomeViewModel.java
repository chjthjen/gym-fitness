package com.example.gymfitness.viewmodels;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gymfitness.data.database.FitnessDB;
import com.example.gymfitness.data.entities.Workout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeViewModel extends ViewModel {
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Workout");
    private MutableLiveData<ArrayList<Workout>> workoutsLiveData = new MutableLiveData<>();
    public LiveData<ArrayList<Workout>> getWorkouts() {
        return workoutsLiveData;
    }
    private FitnessDB fitnessDB;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private String userLevel;
    public void setUserLevel(Context context)
    {
        fitnessDB = FitnessDB.getInstance(context);
        executorService.execute(() -> {
            userLevel = fitnessDB.userInformationDAO().getUserInformation().getLevel();
        });
    }

    public void loadWorkoutsByLevel() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Workout> workoutList = new ArrayList<>();
                for (DataSnapshot workoutSnapshot : dataSnapshot.getChildren()) {
                    Workout workout = workoutSnapshot.getValue(Workout.class);
                    workout.setWorkout_name(workoutSnapshot.getKey());
                    if(Objects.equals(workout.getLevel(),userLevel))
                    {
                        workoutList.add(workout);
                    }
                }
                workoutsLiveData.setValue(workoutList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
