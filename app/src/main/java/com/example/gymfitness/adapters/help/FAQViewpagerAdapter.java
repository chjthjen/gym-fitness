package com.example.gymfitness.adapters.help;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.gymfitness.fragments.help.AccountFAQFragment;
import com.example.gymfitness.fragments.help.GeneralFAQFragment;
import com.example.gymfitness.fragments.help.ServiceFAQFragment;

public class FAQViewpagerAdapter extends FragmentStateAdapter {
    public FAQViewpagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public FAQViewpagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public FAQViewpagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new GeneralFAQFragment();
            case 1:
                return new AccountFAQFragment();
            case 2:
                return new ServiceFAQFragment();
            default:
                return  new GeneralFAQFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
