package com.example.gymfitness.fragments.notification;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.gymfitness.R;
import com.example.gymfitness.databinding.FragmentNotificationsBinding;
import com.example.gymfitness.helpers.NotificationHelper;
import com.example.gymfitness.viewmodels.NotificationsViewModel;

import java.util.Objects;

public class NotificationsFragment extends Fragment {

    FragmentNotificationsBinding binding;
    private boolean isGeneralNotificationOn = true, isSoundOn = true, isDontDisturbOn = true, isVibrateOn = true, isLockScreenOn = true, isRemindersOn = true;

    NotificationsViewModel viewModel;

    public NotificationsFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // set toogle status
        boolean isOnNoti = NotificationHelper.isNotificationsOff(getContext());
        viewModel.toggleLockScreen(binding.imfDontDisturbMode,isOnNoti);

        binding.imfDontDisturbMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean access = NotificationHelper.isDoNotDisturbAccessGranted(getContext());
                if(access)
                {
                    NotificationHelper.toggleNotifications(getContext());
                    boolean status = NotificationHelper.isNotificationsOff(getContext());
                    viewModel.toggleLockScreen(binding.imfDontDisturbMode,status);
                }
                else
                    NotificationHelper.requestDoNotDisturbAccess(getContext());

            }
        });

    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem itemsSearch = menu.findItem(R.id.ic_search);
        MenuItem itemsNotif = menu.findItem(R.id.ic_notif);
        MenuItem itemsProfile = menu.findItem(R.id.ic_profile);
        if (itemsSearch != null && itemsNotif != null && itemsProfile != null) {
            itemsSearch.setVisible(false);
            itemsNotif.setVisible(false);
            itemsProfile.setVisible(false);

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Notifications Settings");

    }
}