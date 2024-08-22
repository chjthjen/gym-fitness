package com.example.gymfitness.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.gymfitness.R;
import com.example.gymfitness.adapters.OnboardingVpAdapter;
import com.example.gymfitness.databinding.ActivityOnBoardBinding;
import com.zhpan.indicator.enums.IndicatorSlideMode;
import com.zhpan.indicator.enums.IndicatorStyle;

public class OnBoardActivity extends AppCompatActivity {
    ActivityOnBoardBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOnBoardBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        binding.indicatorView.setPageSize(3);
        binding.indicatorView.setSliderWidth(50);
        binding.indicatorView.setSliderHeight(10);
        binding.indicatorView.setSliderGap(10);
        binding.indicatorView.setSlideMode(IndicatorSlideMode.SMOOTH);
        binding.indicatorView.setIndicatorStyle(IndicatorStyle.ROUND_RECT);
        binding.indicatorView.notifyDataChanged();
        OnboardingVpAdapter adapter = new OnboardingVpAdapter(this);
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == 2) {
                    binding.skipbtn.setVisibility(View.INVISIBLE);
                } else {
                    binding.skipbtn.setVisibility(TextView.VISIBLE);
                }
                if (position == 2) {
                    binding.button3.setText("Get Started");
                } else {
                    binding.button3.setText("Next");
                }

                if (position == 0) {
                    binding.getRoot().setBackground(getDrawable(R.drawable.onboarding_bg1));
                }
                if (position == 1) {
                    binding.getRoot().setBackground(getDrawable(R.drawable.onboardingbg2));
                }
                else if (position == 2) {
                    binding.getRoot().setBackground(getDrawable(R.drawable.bgr_onboarding_2d));
                }
                binding.indicatorView.onPageSelected(position);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                binding.indicatorView.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        });

//        binding.button3.getBackground().setAlpha(25);
        binding.button3.setOnClickListener(v -> {
            if (binding.viewPager.getCurrentItem() == 2) {
                finish();
            } else {
                binding.viewPager.setCurrentItem(binding.viewPager.getCurrentItem() + 1);
            }
        });

        binding.skipbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.viewPager.setCurrentItem(2);
            }
        });
    }
}