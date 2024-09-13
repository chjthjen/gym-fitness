package com.example.gymfitness.fragments.setup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.gymfitness.R;
import com.example.gymfitness.databinding.FragmentPhysicalActivityLevelBinding;
import com.example.gymfitness.viewmodels.SetUpViewModel;

public class PhysicalActivityFragment extends Fragment {

    private FragmentPhysicalActivityLevelBinding binding;
    private SetUpViewModel setUpViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPhysicalActivityLevelBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Khởi tạo ViewModel
        setUpViewModel = new ViewModelProvider(this).get(SetUpViewModel.class);

        // Gắn ViewModel vào binding
        binding.setSetupViewModel(setUpViewModel);
        binding.setLifecycleOwner(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
