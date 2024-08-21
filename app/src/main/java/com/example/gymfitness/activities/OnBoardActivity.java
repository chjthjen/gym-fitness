package com.example.gymfitness.activities;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gymfitness.R;
import com.example.gymfitness.adapters.OnboardingVpAdapter;
import com.example.gymfitness.databinding.ActivityOnBoardBinding;
import com.example.gymfitness.viewmodels.OnBoardViewmodel;

public class OnBoardActivity extends AppCompatActivity {
    ActivityOnBoardBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOnBoardBinding.inflate(getLayoutInflater());
        OnBoardViewmodel onBoardViewmodel = new OnBoardViewmodel(this);
        onBoardViewmodel.setAdapter(new OnboardingVpAdapter(this));
        binding.setViewModel(onBoardViewmodel);
        binding.setLifecycleOwner(this);
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());


    }
}