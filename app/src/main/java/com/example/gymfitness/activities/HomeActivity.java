package com.example.gymfitness.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import com.example.gymfitness.R;
import com.example.gymfitness.databinding.ActivityHomeBinding;
import com.example.gymfitness.utils.UserData;
import com.example.gymfitness.viewmodels.SharedViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private NavHostFragment navHostFragment;
    private NavController navController;
    private BottomNavigationView bottomNavigation;
    private String username;
    private SharedViewModel sharedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbar, (v, insets) -> {
            v.setPadding(insets.getSystemWindowInsetLeft(),
                    insets.getSystemWindowInsetTop(),
                    insets.getSystemWindowInsetRight(),
                    0);
            return insets;
        });
        setSupportActionBar(binding.toolbar);
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);


        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentMainContainerView);
        navController = navHostFragment.getNavController();

        bottomNavigation = binding.navigationBottom;
        // update checked bottom navigation
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            int id = destination.getId();
            int id2 = R.id.homeFragment;
            Log.d("checkid", String.valueOf(id));
            if(id == R.id.homeFragment)
            {
                MenuItem menuItem = bottomNavigation.getMenu().findItem(R.id.ic_homeFragment);
                if (menuItem != null) {
                    menuItem.setChecked(true);
                }
            }else if(id == R.id.resourcesFragment2)
            {
                MenuItem menuItem = bottomNavigation.getMenu().findItem(R.id.ic_resourcesFragment);
                if (menuItem != null) {
                    menuItem.setChecked(true);
                }
            }else if(id == R.id.favoritesFragment)
            {
                MenuItem menuItem = bottomNavigation.getMenu().findItem(R.id.ic_startsFragment);
                if (menuItem != null) {
                    menuItem.setChecked(true);
                }
            }
        });

        username = UserData.getUsername(this);
        sharedViewModel.setUsername(username);
        // setting toolbar
        navController.addOnDestinationChangedListener(((navController, navDestination, bundle) -> {
            if(navDestination.getId() == R.id.homeFragment) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                binding.toolbar.setTitle("Hi, " + username);
            } else {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                binding.toolbar.setNavigationIcon(R.drawable.arrow);
            }
        }));
        // bottom navigation
        NavigationUI.setupWithNavController(bottomNavigation,navController);
        // event
        addEvents();
//        askNotificationPermission();

    }

    private void addEvents()
    {
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());

        binding.navigationBottom.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if(id == R.id.ic_homeFragment){
                navController.navigate(R.id.homeFragment);
                return true;
            } else if(id == R.id.ic_resourcesFragment){
                navController.navigate(R.id.resourcesFragment2);
                return true;
            }
            else if (id == R.id.ic_startsFragment){
                navController.navigate(R.id.favoritesFragment);
                return true;
            }
            return false;
        });
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
        } else if(id == R.id.ic_search){
            navController.navigate(R.id.allSearchFragment);
            return true;
        }
        else if(id == R.id.ic_notif){
            navController.navigate(R.id.allNotificationsFragment);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    public BottomNavigationView getBottomNavigationView() {
        return binding.navigationBottom;
    }

    // Declare the launcher at the top of your Activity/Fragment:
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // FCM SDK (and your app) can post notifications.
                } else {
                    // TODO: Inform user that that your app will not show notifications.
                }
            });

    private void askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Permission needed")
                        .setMessage("This permission is needed because of FCM SDK")
                        .setPositiveButton("OK", (dialog, which) -> {
                            // Directly ask for the permission
                            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
                        })
                        .setNegativeButton("Cancel", (dialog, which) -> {
                            // Do nothing
                        })
                        .create()
                        .show();
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }
    }
}
