package com.example.gymfitness.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.example.gymfitness.R;
import com.example.gymfitness.databinding.ActivityHomeBinding;
public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private NavHostFragment navHostFragment;
    private NavController navController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentMainContainerView);
        navController = navHostFragment.getNavController();


        // setting toolbar
        navController.addOnDestinationChangedListener(((navController, navDestination, bundle) -> {
            if(navDestination.getId() == R.id.homeFragment)
            {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                binding.toolbar.setTitle("Hi, Madison");
            }
            else
            {
                binding.toolbar.setNavigationIcon(R.drawable.arrow);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }));


        addEvents();
    }


    private void addEvents()
    {
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.ic_profile){
            navController.navigate(R.id.profileFragment);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
