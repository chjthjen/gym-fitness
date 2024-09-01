package com.example.gymfitness.fragments.authentication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.example.gymfitness.data.ApiResponse;
import com.example.gymfitness.databinding.FragmentSetPasswordBinding;
import com.example.gymfitness.retrofit.GymApi;
import com.example.gymfitness.retrofit.RetrofitInstance;
import com.example.gymfitness.viewmodels.AuthViewModel;
import com.example.gymfitness.viewmodels.ResetPasswordViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetPasswordFragment extends Fragment {
    private ResetPasswordViewModel viewModel;
    private FragmentSetPasswordBinding binding;
    private NavController navController;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public SetPasswordFragment() {
    }

    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_set_password, container, false);
        viewModel = new ViewModelProvider(getActivity()).get(ResetPasswordViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        navController = NavHostFragment.findNavController(SetPasswordFragment.this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_setPasswordFragment_to_forgottenPasswordFragment2);
            }
        });

        binding.btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = binding.edtPassword.getText().toString();
                String confirmPass = binding.edtConfirmPassword.getText().toString();
                if (password.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!password.equals(confirmPass)) {
                    Toast.makeText(getContext(), "Confirmation password does not match", Toast.LENGTH_SHORT).show();
                    return;
                }
                SharedPreferences prefs = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                String oobCode = prefs.getString("oobCode", null);
                viewModel.resetPassword(oobCode, password);
            }
        });

        viewModel.getPasswordResetSuccess().observe(getViewLifecycleOwner(), isSuccess ->{
            if(isSuccess){
                Toast.makeText(getContext(), "Successfully", Toast.LENGTH_SHORT).show();
                navController.navigate(R.id.action_setPasswordFragment_to_loginFragment);
            }
        });
    }
}
