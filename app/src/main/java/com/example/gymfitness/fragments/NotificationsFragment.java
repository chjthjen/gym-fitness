package com.example.gymfitness.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.gymfitness.R;
import com.example.gymfitness.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {

    FragmentNotificationsBinding binding;
    private boolean isGeneralNotificationOn = true, isSoundOn = true, isDontDisturbOn = true, isVibrateOn = true, isLockScreenOn = true, isRemindersOn = true;

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

        binding.imgGeneralNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleLockScreen(binding.imgGeneralNotification, isGeneralNotificationOn);
            }
            });

        binding.imgSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleLockScreen(binding.imgSound, isSoundOn);
            }
        });

        binding.imfDontDisturbMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleLockScreen(binding.imfDontDisturbMode, isDontDisturbOn);
            }
        });

        binding.imgVibrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleLockScreen(binding.imgVibrate, isVibrateOn);
            }
        });

        binding.imgLockScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleLockScreen(binding.imgLockScreen, isLockScreenOn);
            }
        });

        binding.imgReminders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleLockScreen(binding.imgReminders, isRemindersOn);
                }
        });

        return binding.getRoot();
    }

    private void toggleLockScreen(ImageButton img, boolean isOn) {
        if (isOn) {
            img.setBackgroundResource(R.drawable.component31_off);
        } else {
            img.setBackgroundResource(R.drawable.component31_on);
        }
        isOn = !isOn;
    }
}