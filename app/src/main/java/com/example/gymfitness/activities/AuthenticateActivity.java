package com.example.gymfitness.activities;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.gymfitness.R;
import com.example.gymfitness.viewmodels.AuthViewModel;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;

public class AuthenticateActivity extends AppCompatActivity {

    private AuthViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_authenticate);
    }

    @Override
    protected void onStart() {
        super.onStart();
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        NavController navController = navHostFragment.getNavController();
        // get oob code from email
        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, pendingDynamicLinkData -> {
                    if (pendingDynamicLinkData != null) {
                        Uri deepLink = pendingDynamicLinkData.getLink();
                        if (deepLink != null) {
                            String oobCode = deepLink.getQueryParameter("oobCode");
                            if (oobCode != null) {
                                SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                                prefs.edit().putString("oobCode", oobCode).apply();
                                Log.i("Successfully Retrieved", oobCode);
                                navController.navigate(R.id.setPasswordFragment);
                            }
                        }
                    }
                })
                .addOnFailureListener(this, e -> {
                    Log.w("AuthenticateActivity", "getDynamicLink:onFailure", e);
                });
    }
}
