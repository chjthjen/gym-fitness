package com.example.gymfitness.fragments.WeeklyChallenge;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.gymfitness.R;
import com.example.gymfitness.activities.HomeActivity;
import com.example.gymfitness.data.entities.Exercise;
import com.example.gymfitness.data.entities.Workout;
import com.example.gymfitness.databinding.FragmentCongratulationBinding;
import com.example.gymfitness.databinding.FragmentWeeklyChallengeCBinding;
import com.example.gymfitness.fragments.HomeFragment;
import com.example.gymfitness.helpers.FavoriteHelper;
import com.example.gymfitness.helpers.ProgressTrackHelper;
import com.example.gymfitness.viewmodels.SharedViewModel;

import java.util.Objects;

public class WeeklyChallengeDFragment extends Fragment {
    FragmentCongratulationBinding binding;
    private SharedViewModel sharedViewModel;
    private NavController navController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_congratulation,container,false);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);
        Workout selectedValue = sharedViewModel.getSelected().getValue();
        binding.hours.setText(selectedValue.getTotalTime() + " hours");
        binding.calorie.setText(selectedValue.getKcal() + " calorie");
        binding.moderate.setText(selectedValue.getLevel());

        binding.btnGoToNextWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy FragmentManager của Activity
                FragmentManager fragmentManager = ((AppCompatActivity) requireActivity()).getSupportFragmentManager();

                // Tạo một transaction để thay thế fragment hiện tại bằng HomeFragment
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentMainContainerView, new HomeFragment()) // Thay thế fragment_container bằng id của ViewGroup chứa fragment
                        .addToBackStack(null) // Thêm vào backstack nếu bạn muốn quay lại fragment trước đó khi nhấn nút back
                        .commit(); // Thực hiện thay thế

                // Hiển thị lại thanh công cụ và thanh điều hướng
                Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
                ((HomeActivity) requireActivity()).getBottomNavigationView().setVisibility(View.VISIBLE);
            }
        });

        binding.btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy FragmentManager của Activity
                FragmentManager fragmentManager = ((AppCompatActivity) requireActivity()).getSupportFragmentManager();

                // Tạo một transaction để thay thế fragment hiện tại bằng HomeFragment
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentMainContainerView, new HomeFragment()) // Thay thế fragment_container bằng id của ViewGroup chứa fragment
                        .addToBackStack(null) // Thêm vào backstack nếu bạn muốn quay lại fragment trước đó khi nhấn nút back
                        .commit(); // Thực hiện thay thế

                // Hiển thị lại thanh công cụ và thanh điều hướng
                Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
                ((HomeActivity) requireActivity()).getBottomNavigationView().setVisibility(View.VISIBLE);
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
        ((HomeActivity) requireActivity()).getBottomNavigationView().setVisibility(View.GONE);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
        ((HomeActivity) requireActivity()).getBottomNavigationView().setVisibility(View.VISIBLE);
    }
}
