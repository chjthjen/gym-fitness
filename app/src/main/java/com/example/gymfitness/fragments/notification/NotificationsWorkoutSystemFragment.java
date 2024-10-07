package com.example.gymfitness.fragments.notification;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gymfitness.adapters.notification.NotificationWorkoutRCVAdapter;
import com.example.gymfitness.data.entities.Notification;
import com.example.gymfitness.databinding.FragmentNotificationsWorkoutSystemBinding;

import java.util.ArrayList;
import java.util.List;

public class NotificationsWorkoutSystemFragment extends Fragment {
    private NotificationWorkoutRCVAdapter timeNotificationWorkoutRCVAdapter;

    public NotificationsWorkoutSystemFragment() {
        // Required empty public constructor
    }

    private FragmentNotificationsWorkoutSystemBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNotificationsWorkoutSystemBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //rcv today
        timeNotificationWorkoutRCVAdapter = new NotificationWorkoutRCVAdapter(getContext());
        LinearLayoutManager todayLinearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        binding.rcvNotificationWorkoutSystem.setLayoutManager(todayLinearLayoutManager);
        timeNotificationWorkoutRCVAdapter.setData(getListTodayNotificaionWorkout());
        binding.rcvNotificationWorkoutSystem.setAdapter(timeNotificationWorkoutRCVAdapter);
    }

    private List<Notification> getListTodayNotificaionWorkout() {
        // Create some example data
        List<Notification> notificationList = new ArrayList<>();
//        notificationList.add(new Notification(R.drawable.star_big_star_on, "You have a new message!", "today", "June 10 - 2:00 PM"));
//        notificationList.add(new Notification(R.drawable.list_on, "scheduled maintenance.", "today", "June 10 - 8:00 AM"));
//        notificationList.add(new Notification(R.drawable.notification_off_circle, "We've detected a login  from a new device", "today", "June 10 - 5:00 AM"));
//        notificationList.add(new Notification(R.drawable.star_big_star_on, "You have a new message!", "today", "June 10 - 2:00 PM"));
//        notificationList.add(new Notification(R.drawable.list_on, "scheduled maintenance.", "today", "June 10 - 8:00 AM"));
//        notificationList.add(new Notification(R.drawable.notification_off_circle, "We've detected a login  from a new device", "today", "June 10 - 5:00 AM"));
//        notificationList.add(new Notification(R.drawable.star_big_star_on, "You have a new message!", "today", "June 10 - 2:00 PM"));
//        notificationList.add(new Notification(R.drawable.list_on, "scheduled maintenance.", "today", "June 10 - 8:00 AM"));
//        notificationList.add(new Notification(R.drawable.notification_off_circle, "We've detected a login  from a new device", "today", "June 10 - 5:00 AM"));
//        notificationList.add(new Notification(R.drawable.star_big_star_on, "You have a new message!", "today", "June 10 - 2:00 PM"));
//        notificationList.add(new Notification(R.drawable.list_on, "scheduled maintenance.", "today", "June 10 - 8:00 AM"));
//        notificationList.add(new Notification(R.drawable.notification_off_circle, "We've detected a login  from a new device", "today", "June 10 - 5:00 AM"));
//        notificationList.add(new Notification(R.drawable.star_big_star_on, "You have a new message!", "today", "June 10 - 2:00 PM"));
//        notificationList.add(new Notification(R.drawable.list_on, "scheduled maintenance.", "today", "June 10 - 8:00 AM"));
//        notificationList.add(new Notification(R.drawable.notification_off_circle, "We've detected a login  from a new device", "today", "June 10 - 5:00 AM"));

        return notificationList;
    }
}