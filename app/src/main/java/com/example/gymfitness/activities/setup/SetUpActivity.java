package com.example.gymfitness.activities.setup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.gymfitness.R;
import com.example.gymfitness.data.DAO.UserInformationDAO;
import com.example.gymfitness.data.database.FitnessDB;
import com.example.gymfitness.databinding.ActivitySetUpBinding;
import com.example.gymfitness.viewmodels.SetUpViewModel;
import com.example.gymfitness.viewmodels.viewmodelsfactory.SetUpViewModelFactory;

public class SetUpActivity extends AppCompatActivity {
    ActivitySetUpBinding binding;
    NavController navController;
    SetUpViewModel setUpViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        // binding
        binding = ActivitySetUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // viewmodel
        UserInformationDAO userInformationDAO = FitnessDB.getInstance(getApplicationContext()).userInformationDAO();
        SetUpViewModelFactory factory = new SetUpViewModelFactory(userInformationDAO);
        setUpViewModel = new ViewModelProvider(this, factory).get(SetUpViewModel.class);
        // nav
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        navController = navHostFragment.getNavController();
        continueControls();
        backControls();

    }

    private void continueControls()
    {
        binding.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentDestId = navController.getCurrentDestination().getId();
                if(currentDestId == R.id.genderFragment)
                {
                    navController.navigate(R.id.action_genderFragment_to_howOldFragment);
                }
                else if(currentDestId == R.id.howOldFragment)
                {
                    navController.navigate(R.id.action_howOldFragment_to_weightFragment);
                }
                else if(currentDestId == R.id.weightFragment)
                {
                    navController.navigate(R.id.action_weightFragment_to_heightFragment);
                }
                else if(currentDestId == R.id.heightFragment)
                {
                    navController.navigate(R.id.action_heightFragment_to_goalFragment);
                }
                else if(currentDestId == R.id.goalFragment)
                {
                    navController.navigate(R.id.action_goalFragment_to_physicalActivityLevelFragment);
                }
                else if(currentDestId == R.id.physicalActivityLevelFragment)
                {
                    binding.btnContinue.setVisibility(View.GONE);
                    navController.navigate(R.id.action_physicalActivityLevelFragment_to_fillProfileFragment);
                }
                else
                    return;
            }
        });
    }



    private void backControls()
    {
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (navController.getPreviousBackStackEntry() != null) {
                    int currentDestId = navController.getCurrentDestination().getId();
                    if(currentDestId == R.id.fillProfileFragment)
                        binding.btnContinue.setVisibility(View.VISIBLE);
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
    public void onBackPressed() {
        int currentDestId = navController.getCurrentDestination().getId();
        if(currentDestId == R.id.fillProfileFragment)
            binding.btnContinue.setVisibility(View.VISIBLE);
        if (navController.getPreviousBackStackEntry() != null) {
            if(currentDestId == R.id.fillProfileFragment)
                binding.btnContinue.setVisibility(View.VISIBLE);
            navController.popBackStack();
        } else {
            Intent intent = new Intent(SetUpActivity.this,SetUpStartActivity.class);
            startActivity(intent);
            super.onBackPressed();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}