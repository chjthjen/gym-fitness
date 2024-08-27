package com.example.gymfitness.fragments.authentication;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.NavHost;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gymfitness.R;
import com.example.gymfitness.databinding.FragmentLoginBinding;
import com.example.gymfitness.viewmodels.AuthViewModel;
public class LoginFragment extends Fragment {

    private AuthViewModel viewModel;
    private  FragmentLoginBinding binding;
    private NavController navController;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public LoginFragment() {
    }

    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_login,container,false);
        viewModel = new ViewModelProvider(getActivity()).get(AuthViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        navController = NavHostFragment.findNavController(LoginFragment.this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_loginFragment_to_signUpFragment);
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInput()) {
                    // Nếu dữ liệu hợp lệ, hiển thị thông báo thành công + xử lí tiếp...
                    Toast.makeText(getActivity(), "Login successful", Toast.LENGTH_SHORT).show();
                } else {
                    // Nếu dữ liệu không hợp lệ, hiển thị thông báo lỗi
                    Toast.makeText(getActivity(), "Invalid input, login failed", Toast.LENGTH_SHORT).show();
                }
            }
            private boolean validateInput() {
                String usernameOrEmail = binding.edtUsername.getText().toString().trim();
                String password = binding.edtPassword.getText().toString().trim();

                // Kiểm tra Username hoặc Email
                if (usernameOrEmail.isEmpty()) {
                    binding.edtUsername.setError("Username or Email cannot be empty");
                    return false;
                }

                // Kiểm tra định dạng email nếu input có chứa ký tự '@'
                if (usernameOrEmail.contains("@") && !Patterns.EMAIL_ADDRESS.matcher(usernameOrEmail).matches()) {
                    binding.edtUsername.setError("Invalid email format");
                    return false;
                }

                // Kiểm tra Password
                if (password.isEmpty()) {
                    binding.edtPassword.setError("Password cannot be empty");
                    return false;
                }

                if (password.length() < 6) {
                    binding.edtPassword.setError("Password must be at least 6 characters long");
                    return false;
                }

                // Nếu tất cả đều hợp lệ
                binding.edtUsername.setError(null);
                binding.edtPassword.setError(null);
                return true;
            }
        });
        binding.tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_loginFragment_to_forgottenPasswordFragment22);
            }
        });
    }


}