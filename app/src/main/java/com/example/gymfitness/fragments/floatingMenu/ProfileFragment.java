package com.example.gymfitness.fragments.floatingMenu;

import static com.example.gymfitness.utils.StringUtils.capitalizeFirstLetter;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.gymfitness.R;
import com.example.gymfitness.data.database.FitnessDB;
import com.example.gymfitness.data.entities.UserInformation;
import com.example.gymfitness.databinding.FragmentProfileBinding;
import com.example.gymfitness.viewmodels.ProfileViewModel;
import com.example.gymfitness.viewmodels.SharedViewModel;

import java.util.Objects;
import java.util.concurrent.Executors;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private ProfileViewModel viewModel;
    private NavController navController;
    private FitnessDB db;
    private SharedViewModel sharedViewModel;

    public ProfileFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        navController = NavHostFragment.findNavController(this);
        db = FitnessDB.getInstance(requireContext());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
        loadUserData();
        addEvents();
    }

    private void loadUserData() {
        Executors.newSingleThreadExecutor().execute(() -> {
            UserInformation userInformation = db.userInformationDAO().getUserInformation();
            requireActivity().runOnUiThread(() -> {
                if (userInformation != null) {
                    Log.d("ProfileFragment", "Gender: " + userInformation.getGender());

                    int imageResId = "male".equalsIgnoreCase(userInformation.getGender()) ? R.drawable.man_profile :
                            "female".equalsIgnoreCase(userInformation.getGender()) ? R.drawable.wonman_profile : R.drawable.account_image;

                    Log.d("ProfileFragment", "Selected image resource ID: " + imageResId);
                    Log.d("ProfileFragment", "Image path: " + userInformation.getImagePath());

                    Glide.with(requireContext())
                            .load(userInformation.getImagePath() != null ? userInformation.getImagePath() : imageResId)
                            .placeholder(R.drawable.bgr_onboarding_2d)
                            .error(R.drawable.arrow_next)
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    Log.e("Glide", "Image load failed", e);
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    return false;
                                }
                            })
                            .into(binding.imageNguoiDung);

                    binding.tvName.setText(capitalizeFirstLetter(userInformation.getFullname()));
                    binding.tvEmail.setText(userInformation.getEmail());
                    binding.tvWeight.setText(String.valueOf(userInformation.getWeight()));
                    binding.tvYearold.setText(String.valueOf(userInformation.getAge()));
                    binding.tvHeigh.setText(String.valueOf(userInformation.getHeight()));
                }
            });
        });
    }



    private void addEvents() {
        binding.btnBack.setOnClickListener(v -> requireActivity().onBackPressed());
        binding.favoriteContainer.setOnClickListener(v -> navController.navigate(R.id.action_profileFragment_to_favoritesFragment));
        binding.helpContainer.setOnClickListener(v -> navController.navigate(R.id.action_profileFragment_to_helpFragment));
        binding.profileContainer.setOnClickListener(v -> navController.navigate(R.id.action_profileFragment_to_editProfileFragment));
        binding.settingsContainer.setOnClickListener(v -> navController.navigate(R.id.action_profileFragment_to_settingsFragment));
        binding.yourRoutineContainer.setOnClickListener(v -> navController.navigate(R.id.action_profileFragment_to_ownRoutineFragment));
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
        binding = null;
    }
}