package com.example.gymfitness.fragments.notification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymfitness.adapters.notification.NotificationWorkoutRCVAdapter;
import com.example.gymfitness.data.database.FitnessDB;
import com.example.gymfitness.data.entities.Notification;
import com.example.gymfitness.databinding.FragmentAllNotificationsBinding;
import com.example.gymfitness.utils.SwipeToDeleteCallback;
import com.example.gymfitness.viewmodels.NotificationViewModel;


import java.util.List;
import java.util.concurrent.Executors;

public class AllNotificationsFragment extends Fragment implements SwipeToDeleteCallback.OnItemDeletedListener {
    private NotificationWorkoutRCVAdapter notificationWorkoutRCVAdapter;
    private FragmentAllNotificationsBinding binding;
    private NotificationViewModel notificationViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAllNotificationsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        notificationWorkoutRCVAdapter = new NotificationWorkoutRCVAdapter(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        binding.rcvNotificationWorkoutReminders.setLayoutManager(layoutManager);
        binding.rcvNotificationWorkoutReminders.setAdapter(notificationWorkoutRCVAdapter);

        // Tạo ViewModel
        notificationViewModel = new ViewModelProvider(this).get(NotificationViewModel.class);

        // Lắng nghe sự thay đổi của dữ liệu thông báo
        notificationViewModel.getNotificationList().observe(getViewLifecycleOwner(), new Observer<List<Notification>>() {
            @Override
            public void onChanged(List<Notification> notifications) {
                notificationWorkoutRCVAdapter.setData(notifications);
            }
        });

        // Thiết lập swipe-to-delete
        SwipeToDeleteCallback<NotificationWorkoutRCVAdapter> swipeToDeleteCallback = new SwipeToDeleteCallback<>(notificationWorkoutRCVAdapter, this, getContext());
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchHelper.attachToRecyclerView(binding.rcvNotificationWorkoutReminders);
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
}

