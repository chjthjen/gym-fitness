package com.example.gymfitness.fragments.notification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymfitness.R;
import com.example.gymfitness.adapters.NotificationAdapter;
import com.example.gymfitness.adapters.NotificationWorkoutRCVAdapter;
import com.example.gymfitness.adapters.NotificationsWorkoutVpAdapter;
import com.example.gymfitness.data.database.FitnessDB;
import com.example.gymfitness.data.entities.Notification;
import com.example.gymfitness.databinding.FragmentAllNotificationsBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class AllNotificationsFragment extends Fragment {
    private NotificationWorkoutRCVAdapter notificationWorkoutRCVAdapter;
    private FragmentAllNotificationsBinding binding;
//    private RecyclerView recyclerView;
//    private NotificationAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAllNotificationsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Notifications");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        notificationWorkoutRCVAdapter = new NotificationWorkoutRCVAdapter(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        binding.rcvNotificationWorkoutReminders.setLayoutManager(layoutManager);
        notificationWorkoutRCVAdapter.setData(getListTodayNotificationWorkout());
        binding.rcvNotificationWorkoutReminders.setAdapter(notificationWorkoutRCVAdapter);
    }

    private List<Notification> getListTodayNotificationWorkout() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d - h:mm a", Locale.ENGLISH);
        List<Notification> notificationList = new ArrayList<>();
        try {
            notificationList.add(new Notification("New Workout Is Available", R.drawable.star_big_star_off, "Reminders", dateFormat.parse("June 10 - 10:00 AM")));
            notificationList.add(new Notification("Don't Forget To Drink Water", R.drawable.bulb_on, "Reminders", dateFormat.parse("June 10 - 10:00 AM")));
            notificationList.add(new Notification("New Workout Is Available", R.drawable.star_big_star_off, "System", dateFormat.parse("June 10 - 10:00 AM")));
        }
        catch (Exception ex) {

        }
        return notificationList;
    }
}
