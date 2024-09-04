package com.example.gymfitness.activities.intro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.gymfitness.R;
import com.example.gymfitness.activities.AuthenticateActivity;
import com.example.gymfitness.adapters.OnboardingVpAdapter;
import com.example.gymfitness.databinding.ActivityOnBoardBinding;
import com.example.gymfitness.viewmodels.OnBoardViewmodel;

public class OnBoardActivity extends AppCompatActivity {
    ActivityOnBoardBinding binding;
    OnBoardViewmodel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOnBoardBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        viewModel = new ViewModelProvider(this).get(OnBoardViewmodel.class);

        setContentView(binding.getRoot());
        setupViewPager();

        viewModel.getCurrentItem().observe(this, position -> {
            binding.viewPager.setCurrentItem(position, true);
            updateUI(position);
        });

        binding.button3.setOnClickListener(v -> {
            if (binding.viewPager.getCurrentItem() == 2) {
                Intent intent = new Intent(OnBoardActivity.this, AuthenticateActivity.class);
                startActivity(intent);
                finish();
            } else {
                viewModel.setCurrentItem(binding.viewPager.getCurrentItem() + 1);
            }
        });

        binding.skipbtn.setOnClickListener(v -> viewModel.setCurrentItem(2));
    }
//tach viewPager
    private void setupViewPager() {
        OnboardingVpAdapter adapter = new OnboardingVpAdapter(this);
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                viewModel.setCurrentItem(position);

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
                Intent intent = new Intent(OnBoardActivity.this, AuthenticateActivity.class);
                startActivity(intent);
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
//su li ui
    private void updateUI(int position) {
        if (position == 2) {
            binding.skipbtn.setVisibility(View.INVISIBLE);
            binding.button3.setText("Get Started");
        } else {
            binding.skipbtn.setVisibility(TextView.VISIBLE);
            binding.button3.setText("Next");
        }

        switch (position) {
            case 0:
                binding.getRoot().setBackground(getDrawable(R.drawable.onboarding_bg1));
                  break;
            case 1:
                binding.getRoot().setBackground(getDrawable(R.drawable.onboardingbg2));
                  break;
            case 2:
                binding.getRoot().setBackground(getDrawable(R.drawable.bgr_onboarding_2d));
                  break;
        }
    }

}