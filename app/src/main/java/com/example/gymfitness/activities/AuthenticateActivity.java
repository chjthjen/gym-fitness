package com.example.gymfitness.activities;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.gymfitness.R;
import com.example.gymfitness.databinding.ActivityAuthenticateBinding;
import com.example.gymfitness.fragments.authentication.LoginFragment;
import com.example.gymfitness.fragments.authentication.SetPasswordFragment;
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
                                Log.i("Lấy được rồi nha !!!",oobCode);
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