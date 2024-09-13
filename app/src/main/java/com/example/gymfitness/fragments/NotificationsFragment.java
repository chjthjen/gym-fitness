package com.example.gymfitness.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.gymfitness.R;
import com.example.gymfitness.databinding.FragmentNotificationsBinding;
import com.example.gymfitness.viewmodels.NotificationsViewModel;

public class NotificationsFragment extends Fragment {

    FragmentNotificationsBinding binding;
    private boolean isGeneralNotificationOn = true, isSoundOn = true, isDontDisturbOn = true, isVibrateOn = true, isLockScreenOn = true, isRemindersOn = true;

    NotificationsViewModel viewModel;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public NotificationsFragment() {
        // Required empty public constructor
    }

    public static NotificationsFragment newInstance(String param1, String param2) {
        NotificationsFragment fragment = new NotificationsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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

        binding.imgGeneralNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewModel != null) {
                    viewModel.toggleLockScreen(binding.imgGeneralNotification, isGeneralNotificationOn);
                    isGeneralNotificationOn = !isGeneralNotificationOn;
                } else {
                    Log.e("NotificationsFragment", "ViewModel is null!");
                }
            }
        });
        binding.imgSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewModel != null) {
                    viewModel.toggleLockScreen(binding.imgSound, isSoundOn);
                    isSoundOn = !isSoundOn;
                } else {
                    Log.e("NotificationsFragment", "ViewModel is null!");
                }
            }
        });

        binding.imfDontDisturbMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewModel != null) {
                    viewModel.toggleLockScreen(binding.imfDontDisturbMode, isDontDisturbOn);
                    isDontDisturbOn = !isDontDisturbOn;
                } else {
                    Log.e("NotificationsFragment", "ViewModel is null!");
                }
            }
        });

        binding.imgVibrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewModel != null) {
                    viewModel.toggleLockScreen(binding.imgVibrate, isVibrateOn);
                    isVibrateOn = !isVibrateOn;
                } else {
                    Log.e("NotificationsFragment", "ViewModel is null!");
                }
            }
        });

        binding.imgLockScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewModel != null) {
                    viewModel.toggleLockScreen(binding.imgLockScreen, isLockScreenOn);
                    isLockScreenOn = !isLockScreenOn;
                }else {
                    Log.e("NotificationsFragment", "ViewModel is null!");
                }
            }
        });

        binding.imgReminders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewModel != null) {
                    viewModel.toggleLockScreen(binding.imgReminders, isRemindersOn);
                    isRemindersOn = !isRemindersOn;
                }else {
                    Log.e("NotificationsFragment", "ViewModel is null!");
                }
            }
        });
    }
}