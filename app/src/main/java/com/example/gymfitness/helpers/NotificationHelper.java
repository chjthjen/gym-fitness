package com.example.gymfitness.helpers;


import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

public class NotificationHelper {

    // check
    public static boolean isDoNotDisturbAccessGranted(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            return notificationManager.isNotificationPolicyAccessGranted();
        } else {
            return false;
        }
    }

    // request access
    public static void requestDoNotDisturbAccess(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
            context.startActivity(intent);
        }
    }

    public static boolean isNotificationsOff(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M
                && notificationManager.isNotificationPolicyAccessGranted()) {
            return notificationManager.getCurrentInterruptionFilter() == NotificationManager.INTERRUPTION_FILTER_NONE;
        }

        return false;
    }

    public static void toggleNotifications(Context context) {
        boolean enable = isNotificationsOff(context);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M
                && notificationManager.isNotificationPolicyAccessGranted()) {
            if (enable) {
                notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL);
            } else {
                notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_NONE);
            }
        }
    }

}
