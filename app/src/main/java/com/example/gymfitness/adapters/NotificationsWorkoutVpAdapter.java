package com.example.gymfitness.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.gymfitness.fragments.NotificationsWorkoutRemindersFragment;
import com.example.gymfitness.fragments.NotificationsWorkoutSystemFragment;

public class NotificationsWorkoutVpAdapter extends FragmentStateAdapter {

    public NotificationsWorkoutVpAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new NotificationsWorkoutRemindersFragment();
            case 1:
                return new NotificationsWorkoutSystemFragment();
            default:
                return new NotificationsWorkoutRemindersFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
