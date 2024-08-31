package com.example.gymfitness.fragments.authentication;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetPasswordFragment extends Fragment {
    private AuthViewModel viewModel;
    private FragmentSetPasswordBinding binding;
    private NavController navController;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    GymApi apiService = RetrofitInstance.getApiService();

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
            public void onClick(View v) {
                String password = binding.edtPassword.getText().toString();
                String confirmPass = binding.edtConfirmPassword.getText().toString();
                String email = ""; // Cung cấp email người dùng

                if (password.equals(confirmPass)) {
                    if (authenticateUser(email)) {
                        updatePasswordInSql(email, password);
                    } else {
                        Toast.makeText(getContext(), "Người dùng không hợp lệ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                }
            }

            private void updatePasswordInSql(String email, String newPassword) {
                // Giả định bạn đã có phương thức để gọi API cập nhật mật khẩu trong SQL
                Call<ApiResponse> call = apiService.updatePassword(email, newPassword);
                call.enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                        if (response.isSuccessful()) {
                            // Cập nhật mật khẩu thành công trong SQL
                            updatePasswordInFirebase(email, newPassword);
                        } else {
                            Toast.makeText(getContext(), "Cập nhật mật khẩu trong SQL thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                    private void updatePasswordInFirebase(String email, String newPassword) {
                        FirebaseAuth auth = FirebaseAuth.getInstance();
                        auth.fetchSignInMethodsForEmail(email).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                auth.signInWithEmailAndPassword(email, "Mật khẩu trong sql").addOnCompleteListener(signInTask -> {
                                    if (signInTask.isSuccessful()) {
                                        FirebaseUser user = auth.getCurrentUser();
                                        user.updatePassword(newPassword).addOnCompleteListener(updateTask -> {
                                            if (updateTask.isSuccessful()) {
                                                Toast.makeText(getContext(), "Mật khẩu đã được cập nhật trên Firebase", Toast.LENGTH_SHORT).show();
                                                // Chuyển hướng đến trang đăng nhập
                                                navController.navigate(R.id.action_setPasswordFragment_to_loginFragment);
                                            } else {
                                                Toast.makeText(getContext(), "Cập nhật mật khẩu trên Firebase thất bại", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    } else {
                                        Toast.makeText(getContext(), "Đăng nhập không thành công với mật khẩu cũ", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                Toast.makeText(getContext(), "Email không tồn tại", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }


                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                        Toast.makeText(getContext(), "Lỗi API", Toast.LENGTH_SHORT).show();
                    }
                });
            }


            private boolean authenticateUser(String email) {
                return true;
            }
        });


    }
}
