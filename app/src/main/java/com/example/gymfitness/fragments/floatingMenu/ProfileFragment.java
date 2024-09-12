package com.example.gymfitness.fragments.floatingMenu;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gymfitness.R;
import com.example.gymfitness.databinding.FragmentProfileBinding;
import com.example.gymfitness.viewmodels.ProfileViewModel;

import java.util.Objects;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private ProfileViewModel viewModel;
    private NavController navController;
    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        navController = NavHostFragment.findNavController(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
        addEvents();
    }

    private void addEvents()
    {
        // show logout dialog
        binding.logoutContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogoutModalBottomSheetFragment bottomSheetDialog = new LogoutModalBottomSheetFragment();
                bottomSheetDialog.show(getChildFragmentManager(), bottomSheetDialog.getTag());
            }
        });

        binding.btnBack.setOnClickListener(v -> requireActivity().onBackPressed());
        // open favorite
        binding.favoriteContainer.setOnClickListener( v -> navController.navigate(R.id.action_profileFragment_to_favoritesFragment));
        binding.helpContainer.setOnClickListener(v -> navController.navigate(R.id.action_profileFragment_to_helpFragment));
        binding.profileContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
                navController.navigate(R.id.action_profileFragment_to_fragment_6_1_1_a2);
            }
        });
        binding.settingsContainer.setOnClickListener(v -> navController.navigate(R.id.action_profileFragment_to_settingsFragment));
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
        binding = null;
    }


}