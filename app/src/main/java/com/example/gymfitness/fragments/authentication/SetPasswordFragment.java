package com.example.gymfitness.fragments.authentication;

import android.os.Bundle;
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
import com.example.gymfitness.databinding.FragmentSetPasswordBinding;
import com.example.gymfitness.viewmodels.AuthViewModel;

public class SetPasswordFragment extends Fragment {
    private AuthViewModel viewModel;
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
        viewModel = new ViewModelProvider(getActivity()).get(AuthViewModel.class);
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
            public void onClick(View view) {
                if (validateInput()) {
                    // Nếu dữ liệu hợp lệ, hiển thị thông báo thành công + xử lý tiếp...
                    Toast.makeText(getActivity(), "Successful", Toast.LENGTH_SHORT).show();
                } else {
                    // Nếu dữ liệu không hợp lệ, hiển thị thông báo lỗi
                    Toast.makeText(getActivity(), "Invalid input", Toast.LENGTH_SHORT).show();
                }
            }
            private boolean validateInput() {
                String password = binding.edtPassword.getText().toString().trim();
                String confirmPassword = binding.edtConfirmPassword.getText().toString().trim();

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
                binding.edtPassword.setError(null);
                binding.edtConfirmPassword.setError(null);
                return true;
            }
        });
    }
}
