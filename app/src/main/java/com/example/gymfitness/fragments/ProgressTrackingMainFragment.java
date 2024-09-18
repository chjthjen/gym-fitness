package com.example.gymfitness.fragments;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.gymfitness.R;
import com.example.gymfitness.adapters.ProgressTrackingViewpagerAdapter;
import com.example.gymfitness.adapters.help.HelpViewpagerAdapter;
import com.example.gymfitness.data.DAO.UserInformationDAO;
import com.example.gymfitness.data.database.FitnessDB;
import com.example.gymfitness.data.entities.UserInformation;
import com.example.gymfitness.databinding.FragmentProgressTrackingMainBinding;
import com.example.gymfitness.utils.UserData;
import com.example.gymfitness.viewmodels.EditProfileViewModel;
import com.example.gymfitness.viewmodels.ProgressTrackingMainViewModel;
import com.example.gymfitness.viewmodelsfactory.EditProfileViewModelFactory;
import com.example.gymfitness.viewmodelsfactory.ProgressTrackingMainFactory;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Objects;

public class ProgressTrackingMainFragment extends Fragment {
    FragmentProgressTrackingMainBinding binding;
    ProgressTrackingMainViewModel viewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_progress_tracking_main, container, false);
        binding = FragmentProgressTrackingMainBinding.bind(view);

        UserInformationDAO userInformationDAO = FitnessDB.getInstance(getContext()).userInformationDAO();
        ProgressTrackingMainFactory factory = new ProgressTrackingMainFactory(userInformationDAO);
        viewModel = new ViewModelProvider(this, factory).get(ProgressTrackingMainViewModel.class);
        viewModel.loadUserInformation();

        viewModel.getUserInformation().observe(getViewLifecycleOwner(), new Observer<UserInformation>() {
            @Override
            public void onChanged(UserInformation userInfo) {
                if (userInfo != null) {

                    int imageResId;
                    if ("male".equalsIgnoreCase(userInfo.getGender())) {
                        imageResId = R.drawable.man_profile;
                    } else if ("female".equalsIgnoreCase(userInfo.getGender())) {
                        imageResId = R.drawable.wonman_profile;
                    } else {
                        imageResId = R.drawable.account_image;
                    }

                    Glide.with(requireContext())
                            .load(userInfo.getImg() != null ? userInfo.getImg() : imageResId)
                            .placeholder(R.drawable.bgr_onboarding_2d)
                            .error(R.drawable.arrow_next)
                            .into(binding.imageUser);

                    binding.tvName.setText(userInfo.getFullname());
                    binding.tvWeight.setText(userInfo.getWeight() + " KG");
                    binding.tvAge.setText("Age: " + userInfo.getAge());
                    binding.tvHeight.setText(userInfo.getHeight() + " CM");
                }
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpTabs();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Progess Tracking");

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