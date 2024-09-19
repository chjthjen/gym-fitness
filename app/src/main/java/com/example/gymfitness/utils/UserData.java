package com.example.gymfitness.utils;

import android.content.Context;

import com.example.gymfitness.data.database.FitnessDB;
import com.example.gymfitness.data.entities.UserInformation;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UserData {

    public static String getUserLevel(Context context)
    {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> future = executorService.submit(() -> {
            FitnessDB fitnessDB = FitnessDB.getInstance(context);
            return fitnessDB.userInformationDAO().getUserInformation().getLevel();
        });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getUsername(Context context)
    {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> future = executorService.submit(() -> {
            FitnessDB fitnessDB = FitnessDB.getInstance(context);
            return fitnessDB.userInformationDAO().getUserInformation().getFullname();
        });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }
}
