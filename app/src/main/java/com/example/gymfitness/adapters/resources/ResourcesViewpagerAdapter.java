package com.example.gymfitness.adapters.resources;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.gymfitness.fragments.resources.ArticleDetailFragment;
import com.example.gymfitness.fragments.resources.ArticleResourceFragment;
import com.example.gymfitness.fragments.resources.WorkoutResourceFragment;

public class ResourcesViewpagerAdapter extends FragmentStateAdapter {
    public ResourcesViewpagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public ResourcesViewpagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public ResourcesViewpagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new WorkoutResourceFragment();
            case 1:
                return new ArticleResourceFragment();
            default:
                return new WorkoutResourceFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
