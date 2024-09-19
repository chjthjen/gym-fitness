package com.example.gymfitness.viewmodels;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gymfitness.data.database.FitnessDB;
import com.example.gymfitness.data.entities.Article;
import com.example.gymfitness.data.entities.Exercise;
import com.example.gymfitness.data.entities.FavoriteWorkout;
import com.example.gymfitness.data.entities.Round;
import com.example.gymfitness.data.entities.Workout;
import com.example.gymfitness.firebase.FirebaseRepository;
import com.example.gymfitness.helpers.FavoriteHelper;
import com.example.gymfitness.utils.Resource;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WorkoutViewModel extends ViewModel {
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Workout");
    private MutableLiveData<ArrayList<Workout>> workoutsLiveData = new MutableLiveData<>();
    private FirebaseRepository firebaseRepository = new FirebaseRepository();
    public LiveData<ArrayList<Workout>> getWorkouts() {
        return workoutsLiveData;
    }
    private String userLevel;
    private final MutableLiveData<Resource<ArrayList<Workout>>> workoutsLD = new MutableLiveData<>();
    public LiveData<Resource<ArrayList<Workout>>> getWorkout() {
        return workoutsLD;
    }
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private FitnessDB fitnessDB;


    public void loadWorkouts() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Workout> workoutList = new ArrayList<>();
                for (DataSnapshot workoutSnapshot : dataSnapshot.getChildren()) {
                    Workout workout = workoutSnapshot.getValue(Workout.class);
                    workout.setWorkout_name(workoutSnapshot.getKey());
                    ArrayList<Round> roundsList = new ArrayList<>();
                    for (DataSnapshot roundSnapshot : workoutSnapshot.child("round").getChildren()) {
                        Round round = roundSnapshot.getValue(Round.class);
                        round.setRound_name(roundSnapshot.getKey());
                        ArrayList<Exercise> exercisesList = new ArrayList<>();
                        for (DataSnapshot exerciseSnapshot : roundSnapshot.getChildren()) {
                            Exercise exercise = exerciseSnapshot.getValue(Exercise.class);
                            exercise.setExercise_name(exerciseSnapshot.getKey());
                            exercisesList.add(exercise);
                        }
                        round.setExercises(exercisesList);
                        roundsList.add(round);
                    }
                    workout.setRound(roundsList);
                    workoutList.add(workout);
                }
                workoutsLiveData.setValue(workoutList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("WorkoutViewModel", "Database error: " + databaseError.getMessage());
            }
        });
    }
    public void loadAllWorkouts() {
        firebaseRepository.getAllWorkouts(new FirebaseRepository.WorkoutCallback() {
            @Override
            public void onCallback(List<Workout> workouts) {
                workoutsLiveData.setValue(new ArrayList<>(workouts));
            }

            @Override
            public void onError(DatabaseError error) {
                Log.e("WorkoutViewModel", "Database error: " + error.getMessage());
            }
        });
    }
    public void setUserLevel(Context context) {
        fitnessDB = FitnessDB.getInstance(context);
        executorService.execute(() -> {
            userLevel = fitnessDB.userInformationDAO().getUserInformation().getLevel();
        });
    }
    public void loadWorkoutsByLevel() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Workout> workoutList = new ArrayList<>();
                for (DataSnapshot workoutSnapshot : dataSnapshot.getChildren()) {
                    Workout workout = workoutSnapshot.getValue(Workout.class);
                    workout.setWorkout_name(workoutSnapshot.getKey());
                    ArrayList<Round> roundsList = new ArrayList<>();
                    for (DataSnapshot roundSnapshot : workoutSnapshot.child("round").getChildren()) {
                        Round round = roundSnapshot.getValue(Round.class);
                        round.setRound_name(roundSnapshot.getKey());
                        ArrayList<Exercise> exercisesList = new ArrayList<>();
                        for (DataSnapshot exerciseSnapshot : roundSnapshot.getChildren()) {
                            Exercise exercise = exerciseSnapshot.getValue(Exercise.class);
                            exercise.setExercise_name(exerciseSnapshot.getKey());
                            exercisesList.add(exercise);
                        }
                        round.setExercises(exercisesList);
                        roundsList.add(round);
                    }
                    workout.setRound(roundsList);
                    if(Objects.equals(userLevel, workout.getLevel()))
                        workoutList.add(workout);
                }
                workoutsLiveData.setValue(workoutList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("WorkoutViewModel", "Database error: " + databaseError.getMessage());
            }
        });
    }

    public void searchWorkouts(String searchText) {
        if (TextUtils.isEmpty(searchText)) {
            loadWorkouts();
            return;
        }

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Workout> workoutList = new ArrayList<>();
                for (DataSnapshot workoutSnapshot : dataSnapshot.getChildren()) {
                    Workout workout = workoutSnapshot.getValue(Workout.class);
                    workout.setWorkout_name(workoutSnapshot.getKey());
                    ArrayList<Round> roundsList = new ArrayList<>();
                    for (DataSnapshot roundSnapshot : workoutSnapshot.child("round").getChildren()) {
                        Round round = roundSnapshot.getValue(Round.class);
                        round.setRound_name(roundSnapshot.getKey());
                        ArrayList<Exercise> exercisesList = new ArrayList<>();
                        for (DataSnapshot exerciseSnapshot : roundSnapshot.getChildren()) {
                            Exercise exercise = exerciseSnapshot.getValue(Exercise.class);
                            exercise.setExercise_name(exerciseSnapshot.getKey());
                            exercisesList.add(exercise);
                        }
                        round.setExercises(exercisesList);
                        roundsList.add(round);
                    }
                    workout.setRound(roundsList);

                    if (workout.getWorkout_name().toLowerCase().contains(searchText.toLowerCase())) {
                        workoutList.add(workout);
                    }
                }
                workoutsLiveData.setValue(workoutList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("WorkoutViewModel", "Database error: " + databaseError.getMessage());
            }
        });
    }



//    public void loadArticlesByFavorite( Context context) {
//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                ArrayList<Workout> workoutList = new ArrayList<>();
//                for (DataSnapshot workoutSnapshot : dataSnapshot.getChildren()) {
//                    Workout workout = workoutSnapshot.getValue(Workout.class);
//                    workout.setWorkout_name(workoutSnapshot.getKey());
//                    ArrayList<Round> roundsList = new ArrayList<>();
//                    for (DataSnapshot roundSnapshot : workoutSnapshot.child("round").getChildren()) {
//                        Round round = roundSnapshot.getValue(Round.class);
//                        round.setRound_name(roundSnapshot.getKey());
//                        ArrayList<Exercise> exercisesList = new ArrayList<>();
//                        for (DataSnapshot exerciseSnapshot : roundSnapshot.getChildren()) {
//                            Exercise exercise = exerciseSnapshot.getValue(Exercise.class);
//                            exercise.setExercise_name(exerciseSnapshot.getKey());
//                            exercisesList.add(exercise);
//                        }
//                        round.setExercises(exercisesList);
//                        roundsList.add(round);
//                    }
//                    workout.setRound(roundsList);
//                   List<FavoriteWorkout> lsFavoriteWorkouts = FavoriteHelper.getListFavoriteWorkout(context);
//                   for(FavoriteWorkout f : lsFavoriteWorkouts)
//                   {
//                       if(Objects.equals(workout.getWorkout_name(), f.getWorkout_name()))
//                           workoutList.add(workout);
//                   }
//
//                }
//                workoutsLiveData.setValue(workoutList);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.e("WorkoutViewModel", "Database error: " + databaseError.getMessage());
//            }
//        });
//    }

    public void loadWorkoutsByFavorite(Context context){
        workoutsLD.setValue(new Resource.Loading<>());
        firebaseRepository.getFavoriteWorkout(new FirebaseRepository.WorkoutCallback() {
            @Override
            public void onCallback(List<Workout> wokouts) {
                Resource<ArrayList<Workout>> resource = new Resource.Success<>(new ArrayList<>(wokouts));
                workoutsLD.postValue(resource);
                Log.d("TAG", "onCallback: " + resource.getData());
            }

            @Override
            public void onError(DatabaseError error) {
                workoutsLD.setValue(new Resource.Error<>(error.getMessage()));
            }
        }, context);
    }
}
