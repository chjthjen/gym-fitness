package com.example.gymfitness.activities.setup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.gymfitness.R;
import com.example.gymfitness.databinding.ActivitySetUpBinding;

public class SetUpActivity extends AppCompatActivity {
    ActivitySetUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySetUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        NavController navController = navHostFragment.getNavController();
        binding.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentDestId = navController.getCurrentDestination().getId();
                if(currentDestId == R.id.genderFragment)
                {
                    navController.navigate(R.id.action_genderFragment_to_howOldFragment);
                    binding.btnContinue.setVisibility(View.VISIBLE);
                }
                else if(currentDestId == R.id.howOldFragment)
                {
                    navController.navigate(R.id.action_howOldFragment_to_weightFragment);
                    binding.btnContinue.setVisibility(View.VISIBLE);
                }
                else if(currentDestId == R.id.weightFragment)
                {
                    navController.navigate(R.id.action_weightFragment_to_heightFragment);
                    binding.btnContinue.setVisibility(View.VISIBLE);
                }
                else if(currentDestId == R.id.heightFragment)
                {
                    navController.navigate(R.id.action_heightFragment_to_goalFragment);
                    binding.btnContinue.setVisibility(View.VISIBLE);
                }
                else if(currentDestId == R.id.goalFragment)
                {
                    navController.navigate(R.id.action_goalFragment_to_physicalActivityLevelFragment);
                    binding.btnContinue.setVisibility(View.VISIBLE);
                }
                else if(currentDestId == R.id.physicalActivityLevelFragment)
                {
                    navController.navigate(R.id.action_physicalActivityLevelFragment_to_fillProfileFragment);
                    binding.btnContinue.setVisibility(View.GONE);
                }
                else
                    return;
            }
        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (navController.getPreviousBackStackEntry() != null) {
                    navController.popBackStack();
                } else {
                    Intent intent = new Intent(SetUpActivity.this,SetUpStartActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}