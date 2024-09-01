package com.example.gymfitness.fragments.authentication;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.gymfitness.R;
import com.example.gymfitness.databinding.FragmentForgottenPasswordBinding;
import com.example.gymfitness.viewmodels.ForgottenPasswordViewModel;
import com.example.gymfitness.viewmodels.SignUpViewModel;

public class ForgottenPasswordFragment extends Fragment {

    private ForgottenPasswordViewModel viewModel;
    private FragmentForgottenPasswordBinding binding;
    private NavController navController;
    private String email;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_forgotten_password, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ForgottenPasswordViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        navController = NavHostFragment.findNavController(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnContinue.setOnClickListener(v -> {
            email = binding.fpEdtEnterYour.getText().toString().trim();
            if (TextUtils.isEmpty(email)) {
                binding.fpTvInput.setText("Please enter your email");
                return;
            }
            viewModel.checkEmail(email);
        });

        binding.btnBack.setOnClickListener(v -> navController.navigate(R.id.action_forgottenPasswordFragment2_to_loginFragment));

        viewModel.getEmailExists().observe(getViewLifecycleOwner(), exists -> {
            email = binding.fpEdtEnterYour.getText().toString().trim();
            if (exists) {
                viewModel.sendResetPasswordLink(email);
                Toast.makeText(getContext(), "Please check email", Toast.LENGTH_SHORT).show();
                navController.navigate(R.id.action_forgottenPasswordFragment2_to_setPasswordFragment);
            }
        });

        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                binding.fpTvInput.setText(error);
            }
        });
    }
}
