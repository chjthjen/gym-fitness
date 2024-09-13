package com.example.gymfitness.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.gymfitness.R;
import com.example.gymfitness.databinding.FragmentSettingsBinding;

import java.util.Objects;

public class SettingsFragment extends Fragment {



    private NavController navController;
    private FragmentSettingsBinding binding;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        if (binding != null && binding.btnNotification != null) {
            binding.lnNotif.setOnClickListener(v -> navController.navigate(R.id.action_settingsFragment_to_notificationFrag));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Settings");
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem itemsSearch = menu.findItem(R.id.ic_search);
        MenuItem itemsNotif = menu.findItem(R.id.ic_notif);
        MenuItem itemsProfile = menu.findItem(R.id.ic_profile);
        if(itemsSearch != null && itemsNotif != null && itemsProfile != null)
        {
            itemsSearch.setVisible(false);
            itemsNotif.setVisible(false);
            itemsProfile.setVisible(false);

        }
    }


}