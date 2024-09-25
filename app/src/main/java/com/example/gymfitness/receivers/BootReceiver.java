package com.example.gymfitness.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.gymfitness.utils.ScheduleNotification;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            ScheduleNotification.setMorningReminder(context);
            ScheduleNotification.setDailyReminders(context);
        }
    }
}
