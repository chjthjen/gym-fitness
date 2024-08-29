package com.example.gymfitness.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gymfitness.R;

public class LaunchActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    private void addControls()
    {
        sharedPreferences = getSharedPreferences("remember", MODE_PRIVATE);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_launch);
        addControls();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int onboarded = sharedPreferences.getInt("status",0);
                if(onboarded == 1)
                {
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("status",1);
                    editor.apply();
                    Intent intent = new Intent(getApplicationContext(), OnBroading_2a.class);
                    startActivity(intent);
                    finish();
                }

            }
        },2000);
    }

}