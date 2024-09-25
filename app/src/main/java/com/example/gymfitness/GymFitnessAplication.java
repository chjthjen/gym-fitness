package com.example.gymfitness;

import android.app.Application;
import android.content.SharedPreferences;

import com.example.gymfitness.utils.ScheduleNotification;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
public class GymFitnessAplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        ScheduleNotification.setMorningReminder(this);
        ScheduleNotification.setDailyReminders(this);

    }
    }

