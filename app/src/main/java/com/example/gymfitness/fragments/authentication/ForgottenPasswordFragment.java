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
import com.example.gymfitness.databinding.FragmentForgottenPasswordBinding;
import com.example.gymfitness.databinding.FragmentLoginBinding;
import com.example.gymfitness.viewmodels.AuthViewModel;

public class ForgottenPasswordFragment extends Fragment {
    private AuthViewModel viewModel;
    private FragmentForgottenPasswordBinding binding;
    private NavController navController;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public static ForgottenPasswordFragment newInstance(String param1, String param2) {
        ForgottenPasswordFragment fragment = new ForgottenPasswordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ForgottenPasswordFragment() {
        // Required empty public constructor
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_forgotten_password,container,false);
        viewModel = new ViewModelProvider(getActivity()).get(AuthViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        navController = NavHostFragment.findNavController(ForgottenPasswordFragment.this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_forgottenPasswordFragment2_to_setPasswordFragment);
            }
        });
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_forgottenPasswordFragment2_to_loginFragment);
            }
        });
        binding.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInput()) {
                    // Nếu email hợp lệ, hiển thị thông báo thành công + xử lý tiếp...
                    Toast.makeText(getActivity(), "Valid email", Toast.LENGTH_SHORT).show();
                } else {
                    // Nếu email không hợp lệ, hiển thị thông báo lỗi
                    Toast.makeText(getActivity(), "Invalid email", Toast.LENGTH_SHORT).show();
                }
            }
            private boolean validateInput() {
                String email = binding.fpEdtEnterYour.getText().toString().trim();

                // Kiểm tra Email
                if (email.isEmpty()) {
                    binding.fpEdtEnterYour.setError("Email cannot be empty");
                    return false;
                }

                // Kiểm tra định dạng email nếu input có chứa ký tự '@'
                if (email.contains("@") && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    binding.fpEdtEnterYour.setError("Invalid email format");
                    return false;
                }

                // Kiểm tra định dạng input có phải email hay không
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    binding.fpEdtEnterYour.setError("Please enter email address");
                    return false;
                }

                //nếu email hợp lệ
                binding.fpEdtEnterYour.setError(null);
                return true;
            }
        });
    }
}