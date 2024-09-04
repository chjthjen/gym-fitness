package com.example.gymfitness.fragments.floatingMenu;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gymfitness.R;
import com.example.gymfitness.activities.AuthenticateActivity;
import com.example.gymfitness.databinding.FragmentLogoutModalBottomSheetBinding;
import com.example.gymfitness.viewmodels.LogoutDialogViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class LogoutModalBottomSheetFragment extends BottomSheetDialogFragment {

    private FragmentLogoutModalBottomSheetBinding binding;
    private LogoutDialogViewModel viewModel;
    private GoogleSignInClient mGoogleSignInClient;
    private NavController navController;

    public LogoutModalBottomSheetFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_logout_modal_bottom_sheet, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(LogoutDialogViewModel.class);
        // define GoogleSignInClient for viewModel
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), GoogleSignInOptions.DEFAULT_SIGN_IN);
        viewModel.setGoogleSignInClient(mGoogleSignInClient);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.logout();
                viewModel.getIsLogout().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean isLogout) {
                        if (isLogout) {
                            Toast.makeText(getContext(), "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), AuthenticateActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            getActivity().finish();

                        } else
                            Toast.makeText(getContext(), "Đăng xuất không thành công", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}