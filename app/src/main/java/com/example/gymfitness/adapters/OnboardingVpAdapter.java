package com.example.gymfitness.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.gymfitness.fragments.Onboarding.OnBroading_2c;
import com.example.gymfitness.fragments.Onboarding.Fragment_2d;
import com.example.gymfitness.fragments.Onboarding.OnboardingBFragment;

public class OnboardingVpAdapter extends FragmentStateAdapter {

    public OnboardingVpAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new OnboardingBFragment();
            case 1:
                return new OnBroading_2c();
            case 2:
                return new Fragment_2d();
            default:
                return new OnboardingBFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
