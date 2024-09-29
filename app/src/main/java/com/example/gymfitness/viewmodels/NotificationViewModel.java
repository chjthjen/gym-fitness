package com.example.gymfitness.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.gymfitness.data.database.FitnessDB;
import com.example.gymfitness.data.entities.Notification;

import java.util.List;

public class NotificationViewModel extends AndroidViewModel {
    private LiveData<List<Notification>> notificationList;

    public NotificationViewModel(@NonNull Application application) {
        super(application);
        notificationList = FitnessDB.getInstance(application).notificationDao().getAllNotifications();
    }

    public LiveData<List<Notification>> getNotificationList() {
        return notificationList;
    }
}
