package com.example.gymfitness.fragments.authentication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gymfitness.R;
import com.example.gymfitness.activities.AuthenticateActivity;
import com.example.gymfitness.databinding.FragmentLoginBinding;
import com.example.gymfitness.databinding.FragmentSignUpBinding;
import com.example.gymfitness.viewmodels.AuthViewModel;

public class SignUpFragment extends Fragment {
    private AuthViewModel viewModel;
    FragmentSignUpBinding binding;
    NavController navController;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public SignUpFragment() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false);
        viewModel = new ViewModelProvider(getActivity()).get(AuthViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        navController = NavHostFragment.findNavController(SignUpFragment.this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_signUpFragment_to_loginFragment);
            }
        });
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_signUpFragment_to_loginFragment);

            }
        });
        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInput()) {
                    // Nếu dữ liệu hợp lệ, hiển thị thông báo thành công + xử lý tiếp...
                    Toast.makeText(getActivity(), "Sign up successful", Toast.LENGTH_SHORT).show();
                } else {
                    // Nếu dữ liệu không hợp lệ, hiển thị thông báo lỗi
                    Toast.makeText(getActivity(), "Invalid input, Sign up failed", Toast.LENGTH_SHORT).show();
                }
            }
            private boolean validateInput() {
                String fullName = binding.edtFullname.getText().toString().trim();
                String emailOrMobile = binding.edtEmailOrMobile.getText().toString().trim();
                String password = binding.edtPassword.getText().toString().trim();
                String confirmPassword = binding.edtConfirmPassword.getText().toString().trim();

                // Kiểm tra Full Name
                if (fullName.isEmpty()) {
                    binding.edtFullname.setError("Full Name cannot be empty");
                    return false;
                }

                // Kiểm tra Email hoặc Mobile Number
                if (emailOrMobile.isEmpty()) {
                    binding.edtEmailOrMobile.setError("Email or Mobile Number cannot be empty");
                    return false;
                }

                // Nếu input có ký tự '@', kiểm tra định dạng email
                if (emailOrMobile.contains("@")) {
                    if (!Patterns.EMAIL_ADDRESS.matcher(emailOrMobile).matches()) {
                        binding.edtEmailOrMobile.setError("Invalid email format");
                        return false;
                    }
                } else {
                    // Nếu không phải email, kiểm tra định dạng số điện thoại
                    if (!Patterns.PHONE.matcher(emailOrMobile).matches()) {
                        binding.edtEmailOrMobile.setError("Invalid mobile number format");
                        return false;
                    }
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

                // Kiểm tra Confirm Password
                if (!confirmPassword.equals(password)) {
                    binding.edtConfirmPassword.setError("Passwords do not match");
                    return false;
                }

                // Nếu tất cả đều hợp lệ
                binding.edtFullname.setError(null);
                binding.edtEmailOrMobile.setError(null);
                binding.edtPassword.setError(null);
                binding.edtConfirmPassword.setError(null);
                return true;
            }
        });
    }
}