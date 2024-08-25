package com.example.gymfitness.activities;

import android.os.Bundle;
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

import com.example.gymfitness.R;
import com.example.gymfitness.databinding.ActivityAuthenticateBinding;
import com.example.gymfitness.fragments.authentication.LoginFragment;
import com.example.gymfitness.viewmodels.AuthViewModel;

public class AuthenticateActivity extends AppCompatActivity {

    private AuthViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ActivityAuthenticateBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_authenticate);
        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, new LoginFragment())
                .commit();
        viewModel.fragmentName.setValue("Log In");
    }

}