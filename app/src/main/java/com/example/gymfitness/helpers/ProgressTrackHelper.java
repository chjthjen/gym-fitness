package com.example.gymfitness.helpers;

import static android.provider.Settings.System.DATE_FORMAT;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.gymfitness.data.database.FitnessDB;
import com.example.gymfitness.data.entities.Exercise;
import com.example.gymfitness.data.entities.ProgressTracking;
import com.example.gymfitness.data.entities.Workout;
import com.example.gymfitness.data.entities.WorkoutLog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProgressTrackHelper {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    static ExecutorService executorService;
    public static void SaveProgress(Workout workout, Exercise exercise, Context context) throws ParseException {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateWithoutTime = sdf.parse(sdf.format(new Date()));
        Log.e("date", dateWithoutTime.toString());
        executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            ProgressTracking progressTracking = new ProgressTracking();
            progressTracking.setDatetime_tracking(dateWithoutTime);
            progressTracking.setExercise_id(exercise.getExercise_name());
            progressTracking.setRep(exercise.getRep());
            progressTracking.setDuration(exercise.getDuration()*exercise.getRep());
            FitnessDB.getInstance(context).progressTrackingDAO().insert(progressTracking);

            WorkoutLog workoutLog = FitnessDB.getInstance(context).workoutLogDAO().checkWorkout(workout.getWorkout_name(), dateWithoutTime);
            if (workoutLog == null) {
                workoutLog = new WorkoutLog();
                workoutLog.setWorkout_name(workout.getWorkout_name());
                workoutLog.setKcal(workout.getKcal());
                workoutLog.setDate(dateWithoutTime);
                workoutLog.setTotalTime(exercise.getDuration());
                FitnessDB.getInstance(context).workoutLogDAO().insertWorkoutLog(workoutLog);
            } else {
                workoutLog.setTotalTime(workoutLog.getTotalTime() + exercise.getDuration() * exercise.getRep());
                FitnessDB.getInstance(context).workoutLogDAO().insertWorkoutLog(workoutLog);
            }
        });
    }

    @TypeConverter
    public static Date parseDate(String dateString) {
        if (dateString == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    @TypeConverter
    public static String fromDateToString(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        return sdf.format(date);
    }
}
