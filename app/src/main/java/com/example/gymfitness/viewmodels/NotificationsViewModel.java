package com.example.gymfitness.viewmodels;

import android.widget.ImageButton;

import androidx.lifecycle.ViewModel;

import com.example.gymfitness.R;

public class NotificationsViewModel extends ViewModel {

    public void toggleLockScreen(ImageButton img, boolean isOn) {
        if (isOn) {
            img.setBackgroundResource(R.drawable.component31_off);
        } else {
            img.setBackgroundResource(R.drawable.component31_on);
        }
    }
}
