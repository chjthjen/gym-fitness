package com.example.gymfitness.receivers;



import android.Manifest;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import com.example.gymfitness.R;
import com.example.gymfitness.activities.LaunchActivity;
import com.example.gymfitness.data.DAO.WorkoutLogDAO;
import com.example.gymfitness.data.database.FitnessDB;
import com.example.gymfitness.data.entities.WorkoutLog;
import com.example.gymfitness.viewmodels.ProgressTrackingViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class NotificationReceiver extends BroadcastReceiver {
    private WorkoutLogDAO workoutLogDAO; // Khai báo DAO ở đây
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    public void onReceive(Context context, Intent intent) {
        workoutLogDAO = FitnessDB.getInstance(context).workoutLogDAO();
        String action = intent.getAction();
        if ("com.example.gymfitness.ACTION_MORNING_REMINDER".equals(action)) {
            showMorningReminder(context);
        } else if ("com.example.gymfitness.ACTION_KCAL_REMINDER".equals(action)) {
            showKcalReminder(context);
        }
    }

    private void showMorningReminder(Context context) {
        sendNotification(context, "Nhắc nhở tập luyện", "Đã đến giờ tập luyện buổi sáng! Hãy bắt đầu nào!", 1);
    }

    private void showKcalReminder(Context context) {
        executorService.execute(() -> {
            int totalKcal = getTotalKcalForToday(context);
            sendNotification(context, "Nhắc nhở kcal", "Tổng kcal hôm nay: " + totalKcal, 2);
        });
    }

    private int getTotalKcalForToday(Context context) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        LocalDate currentDate = LocalDate.now();
        String dateString = currentDate.getYear() + "-" + String.format("%02d", currentDate.getMonthValue()) + "-" + String.format("%02d", currentDate.getDayOfMonth());
        try {
            Date selectedDate = dateFormat.parse(dateString);

            Integer totalKcal = workoutLogDAO.getTotalKcalByDate(selectedDate);
            return totalKcal != null ? totalKcal : 0;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private void sendNotification(Context context, String title, String content, int notificationId) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "daily_reminder_channel";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Daily Reminder", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }

        Intent notificationIntent = new Intent(context, LaunchActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.notification_off)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setFullScreenIntent(pendingIntent, true)
                .setAutoCancel(true);

        notificationManager.notify(notificationId, builder.build());
    }
}