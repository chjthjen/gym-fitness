package com.example.gymfitness.adapters.help;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.gymfitness.fragments.help.ContactUsFragment;
import com.example.gymfitness.fragments.help.FAQFragment;
import com.example.gymfitness.fragments.help.HelpFragment;

public class HelpViewpagerAdapter extends FragmentStateAdapter {


    public HelpViewpagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public HelpViewpagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public HelpViewpagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new FAQFragment();
            case 1:
                return new ContactUsFragment();
            default:
                return new FAQFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
