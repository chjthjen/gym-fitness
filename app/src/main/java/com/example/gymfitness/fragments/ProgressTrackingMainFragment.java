package com.example.gymfitness.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gymfitness.R;
import com.example.gymfitness.adapters.ProgressTrackingViewpagerAdapter;
import com.example.gymfitness.adapters.help.HelpViewpagerAdapter;
import com.example.gymfitness.databinding.FragmentProgressTrackingMainBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ProgressTrackingMainFragment extends Fragment {
    FragmentProgressTrackingMainBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_progress_tracking_main, container, false);
        binding = FragmentProgressTrackingMainBinding.bind(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpTabs();
    }

    private void setUpTabs() {
        binding.viewPager.setAdapter(new ProgressTrackingViewpagerAdapter(getChildFragmentManager(), getLifecycle()));
        new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> {
            switch (position){
                case 0:
                    tab.setText("Workout Log");
                    break;
                case 1:
                    tab.setText("Charts");
                    break;
            }
        }).attach();

        for (int i = 0; i < binding.tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = binding.tabLayout.getTabAt(i);
            View tabView = ((ViewGroup) binding.tabLayout.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) tabView.getLayoutParams();
            layoutParams.setMargins(15, 0, 15, 0);  // Set left and right margins here
            tabView.setLayoutParams(layoutParams);
            tabView.requestLayout();
        }
    }
}