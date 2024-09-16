package com.example.gymfitness.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.gymfitness.fragments.ProgressTrackingFragment;
import com.example.gymfitness.fragments.WorkoutLogFragment;

public class ProgressTrackingViewpagerAdapter extends FragmentStateAdapter {
    public ProgressTrackingViewpagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public ProgressTrackingViewpagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public ProgressTrackingViewpagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new WorkoutLogFragment();
            case 1:
                return new ProgressTrackingFragment();
            default:
                return new WorkoutLogFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
