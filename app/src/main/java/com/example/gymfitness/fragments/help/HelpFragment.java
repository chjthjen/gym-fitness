package com.example.gymfitness.fragments.help;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gymfitness.R;
import com.example.gymfitness.adapters.help.HelpViewpagerAdapter;
import com.example.gymfitness.databinding.FragmentHelpBinding;
import com.example.gymfitness.viewmodels.HelpViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Objects;

public class HelpFragment extends Fragment {

    private HelpViewModel mViewModel;
    FragmentHelpBinding binding;

    public static HelpFragment newInstance() {
        return new HelpFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHelpBinding.inflate(inflater, container, false);
        setUpTabs();
        return binding.getRoot();
    }

    private void setUpTabs() {
        binding.viewPager.setAdapter(new HelpViewpagerAdapter(getChildFragmentManager(), getLifecycle()));
        new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> {
            switch (position){
                case 0:
                    tab.setText("FAQ");
                    break;
                case 1:
                    tab.setText("Contact Us");
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