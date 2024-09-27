package com.example.gymfitness.fragments.notification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymfitness.R;
import com.example.gymfitness.adapters.NotificationAdapter;
import com.example.gymfitness.adapters.NotificationWorkoutRCVAdapter;
import com.example.gymfitness.adapters.NotificationsWorkoutVpAdapter;
import com.example.gymfitness.data.database.FitnessDB;
import com.example.gymfitness.data.entities.Notification;
import com.example.gymfitness.databinding.FragmentAllNotificationsBinding;
import com.example.gymfitness.utils.SwipeToDeleteCallback;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AllNotificationsFragment extends Fragment implements SwipeToDeleteCallback.OnItemDeletedListener {
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
    public void onItemDeleted(Object item) {
        if (item instanceof Notification) {
            Executors.newSingleThreadExecutor().execute(() -> {
                FitnessDB.getInstance(requireContext()).notificationDao().delete((Notification) item);
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(getContext(), "Notification deleted successfully", Toast.LENGTH_SHORT).show()
                );
            });
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        notificationWorkoutRCVAdapter = new NotificationWorkoutRCVAdapter(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        binding.rcvNotificationWorkoutReminders.setLayoutManager(layoutManager);
        notificationWorkoutRCVAdapter.setData(getListTodayNotificationWorkout());
        binding.rcvNotificationWorkoutReminders.setAdapter(notificationWorkoutRCVAdapter);

        SwipeToDeleteCallback<NotificationWorkoutRCVAdapter> swipeToDeleteCallback = new SwipeToDeleteCallback<>(notificationWorkoutRCVAdapter, this, getContext());
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchHelper.attachToRecyclerView(binding.rcvNotificationWorkoutReminders);
    }

    private List<Notification> getListTodayNotificationWorkout() {
        List<Notification> notificationList = new ArrayList<>();
        Future<List<Notification>> future = Executors.newSingleThreadExecutor().submit(() -> FitnessDB.getInstance(requireContext()).notificationDao().getAllNotifications());
        try {
            notificationList.addAll(future.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return notificationList;
    }
}
