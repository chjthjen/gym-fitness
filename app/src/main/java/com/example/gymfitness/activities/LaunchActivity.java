package com.example.gymfitness.activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.gymfitness.R;
import com.example.gymfitness.activities.intro.OnBroading_2a;
import com.example.gymfitness.activities.setup.SetUpStartActivity;
import com.example.gymfitness.data.database.FitnessDB;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LaunchActivity extends AppCompatActivity {
    private static final int NOTIFICATION_PERMISSION_CODE = 1001;
    private SharedPreferences sharedPreferences;
    private FirebaseAuth firebaseAuth;
    private ExecutorService executorService;
    private SharedPreferences setupPrefs;
    private static final int EXACT_ALARM_PERMISSION_REQUEST_CODE = 1002;

    private void addControls() {
        sharedPreferences = getSharedPreferences("remember", MODE_PRIVATE);
        firebaseAuth = FirebaseAuth.getInstance();
        executorService = Executors.newSingleThreadExecutor();
        setupPrefs = getSharedPreferences("UserInformation", MODE_PRIVATE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_launch);
        addControls();

        executorService.execute(() -> {
            FitnessDB.getInstance(getApplicationContext()).userInformationDAO().getUserInformation();
        });
//        checkAndRequestNotificationPermission();
//        requestExactAlarmPermission();
        proceedToNextActivity();
    }

    private void checkAndRequestNotificationPermission() {
        boolean isPermissionRequested = sharedPreferences.getBoolean("isPermissionRequested", false);

        if (!isPermissionRequested) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, NOTIFICATION_PERMISSION_CODE);
                }
            }

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isPermissionRequested", true);
            editor.apply();
        }


        proceedToNextActivity();
    }

    private void proceedToNextActivity() {
        new Handler().postDelayed(() -> {
            int onboarded = sharedPreferences.getInt("status", 0);
            int doneSetUp = setupPrefs.getInt("done", 0);
            if (onboarded == 1) {
                if (doneSetUp == 1) {
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(getApplicationContext(), SetUpStartActivity.class);
                    startActivity(intent);
                    finish();
                }
            } else {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("status", 1);
                editor.apply();
                Intent intent = new Intent(getApplicationContext(), OnBroading_2a.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == NOTIFICATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isPermissionRequested", true);
                editor.apply();
            }
            proceedToNextActivity();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    private boolean hasExactAlarmPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            return checkSelfPermission(Manifest.permission.SCHEDULE_EXACT_ALARM) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }
    private void requestExactAlarmPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!hasExactAlarmPermission()) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SCHEDULE_EXACT_ALARM}, EXACT_ALARM_PERMISSION_REQUEST_CODE);
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null)
            executorService.shutdown();
    }
}