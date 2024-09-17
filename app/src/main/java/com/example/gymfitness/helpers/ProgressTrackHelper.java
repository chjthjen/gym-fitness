package com.example.gymfitness.helpers;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.gymfitness.data.database.FitnessDB;
import com.example.gymfitness.data.entities.ProgressTracking;
import com.example.gymfitness.data.entities.Workout;
import com.example.gymfitness.data.entities.WorkoutLog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.ExecutorService;

public class ProgressTrackHelper {
    static ExecutorService executorService;
    public static void SaveProgress(Workout workout, String exerciseName, Context context) throws ParseException {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateWithoutTime = sdf.parse(sdf.format(new Date()));
        executorService.execute(() -> {
            ProgressTracking progressTracking = new ProgressTracking();
            progressTracking.setDatetime_tracking(dateWithoutTime);
            progressTracking.setExercise_id(exerciseName);
            FitnessDB.getInstance(context).progressTrackingDAO().insert(progressTracking);

            WorkoutLog workoutLog = FitnessDB.getInstance(context).workoutLogDAO().checkWorkout(workout.getWorkout_name(), dateWithoutTime);
            if (workoutLog == null) {
                workoutLog = new WorkoutLog();
                workoutLog.setWorkout_name(workout.getWorkout_name());
                workoutLog.setKcal(workout.getKcal());
                workoutLog.setDate(dateWithoutTime);
                workoutLog.setTotalTime(workout.getTotalTime());
                FitnessDB.getInstance(context).workoutLogDAO().insertWorkoutLog(workoutLog);
            } else {
                workoutLog.setKcal(workoutLog.getKcal() + workout.getKcal());
                workoutLog.setTotalTime(workoutLog.getTotalTime() + workout.getTotalTime());
                FitnessDB.getInstance(context).workoutLogDAO().insertWorkoutLog(workoutLog);
            }
        });
    }
}
