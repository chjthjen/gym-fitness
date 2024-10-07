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
import com.example.gymfitness.databinding.FragmentNotificationsWorkoutRemindersBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NotificationsWorkoutRemindersFragment extends Fragment {
    private NotificationWorkoutRCVAdapter timeNotificationWorkoutRCVAdapter;

    public NotificationsWorkoutRemindersFragment() {
        // Required empty public constructor
    }

    private FragmentNotificationsWorkoutRemindersBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNotificationsWorkoutRemindersBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        timeNotificationWorkoutRCVAdapter = new NotificationWorkoutRCVAdapter(getContext());
        LinearLayoutManager todayLinearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        binding.rcvNotificationWorkoutReminders.setLayoutManager(todayLinearLayoutManager);
        timeNotificationWorkoutRCVAdapter.setData(getListTodayNotificaionWorkout());
        binding.rcvNotificationWorkoutReminders.setAdapter(timeNotificationWorkoutRCVAdapter);
    }

    private List<Notification> getListTodayNotificaionWorkout() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d - h:mm a", Locale.ENGLISH);
        List<Notification> notificationList = new ArrayList<>();
//        try {
//            notificationList.add(new Notification(R.drawable.star_big_star_off, "New Workout Is Available", "today", dateFormat.parse("June 10 - 10:00 AM")));
//            notificationList.add(new Notification(R.drawable.bulb_on, "Dont't Forget To Drink Water", "today", "June 10 - 8:00 AM"));
//            notificationList.add(new Notification(R.drawable.star_big_star_off, "New Workout Is Available", "today", "June 10 - 10:00 AM"));
//        }
//        catch (Exception ex) {
//
//        }

        return notificationList;
    }
}