package com.example.gymfitness.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gymfitness.R;
import com.example.gymfitness.activities.intro.OnBroading_2a;
import com.example.gymfitness.data.Entities.Workout;
import com.example.gymfitness.data.database.FitnessDB;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class LaunchActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private FirebaseAuth firebaseAuth;

    private void addControls() {
        sharedPreferences = getSharedPreferences("remember", MODE_PRIVATE);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_launch);
        addControls();
        // create database
        new Thread(new Runnable() {
            @Override
            public void run() {
                FitnessDB.getInstance(getApplicationContext()).workoutDAO().getAllWorkouts();
            }
        }).start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                if (currentUser != null) {

                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {

                    int onboarded = sharedPreferences.getInt("status", 0);
                    if (onboarded == 1) {
                        Intent intent = new Intent(getApplicationContext(), AuthenticateActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("status", 1);
                        editor.apply();
                        Intent intent = new Intent(getApplicationContext(), OnBroading_2a.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        }, 2000);
    }
}
