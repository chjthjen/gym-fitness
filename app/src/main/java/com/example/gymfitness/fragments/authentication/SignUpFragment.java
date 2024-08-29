package com.example.gymfitness.fragments.authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.gymfitness.R;
import com.example.gymfitness.databinding.FragmentSignUpBinding;
import com.example.gymfitness.data.SignUpCallback;
import com.example.gymfitness.data.UserSignUp;
import com.example.gymfitness.helpers.ValidationEmail;
import com.example.gymfitness.viewmodels.SignUpViewModel;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

import java.util.Arrays;

public class SignUpFragment extends Fragment {
    private SignUpViewModel viewModel;
    private FragmentSignUpBinding binding;
    private NavController navController;
    private ProgressDialog progressDialog;
    private static final int RC_SIGN_IN = SignUpViewModel.RC_SIGN_IN;

    public SignUpFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(SignUpViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        navController = NavHostFragment.findNavController(this);

        setupProgressDialog();
        initializeEventHandlers();

        return binding.getRoot();
    }

    private void setupProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Đăng ký");
        progressDialog.setMessage("Đang đăng ký...");
    }

    private void initializeEventHandlers() {
        binding.googleSignUp.setOnClickListener(v -> signUpWithGoogle());
        binding.facebookSignUp.setOnClickListener(v -> signUpWithFacebook());
        binding.tvLogin.setOnClickListener(v -> navController.navigate(R.id.action_signUpFragment_to_loginFragment));
        binding.btnBack.setOnClickListener(v -> navController.navigate(R.id.action_signUpFragment_to_loginFragment));
        binding.btnSignUp.setOnClickListener(v -> handleSignUp());
    }

    private void handleSignUp() {
        if (validateInput()) {
            UserSignUp userSignUp = new UserSignUp(
                    binding.edtEmailOrMobile.getText().toString(),
                    binding.edtPassword.getText().toString(),
                    binding.edtFullname.getText().toString()
            );
            viewModel.setUser(userSignUp);

            if (viewModel.checkUserStatus()) {
                Toast.makeText(getActivity(), "Vui lòng đăng xuất tài khoản!", Toast.LENGTH_SHORT).show();
            } else {
                progressDialog.show();
                viewModel.signUp(new SignUpCallback() {
                    @Override
                    public void onSuccess() {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Đăng kí thành công", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(int errorCode) {
                        progressDialog.dismiss();
                        if (errorCode == -999) {
                            Toast.makeText(getActivity(), "Gmail này đã được dùng cho tài khoản khác", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getActivity(), "Đăng kí thất bại. vui lòng thử lại", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }
    }

    private boolean validateInput() {

        String usernameOrEmail = binding.edtEmailOrMobile.getText().toString().trim();
        String password = binding.edtPassword.getText().toString().trim();

        // goi kiem tra
        return ValidationEmail.validateInputEmail(
                usernameOrEmail,
                password,
                binding.edtEmailOrMobile,
                binding.edtPassword
        );
    }

    private void signUpWithGoogle() {
        GoogleSignInClient googleSignInClient = viewModel.getGoogleSignInClient(getContext(), getString(R.string.default_web_client_id));
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signUpWithFacebook() {
        CallbackManager callbackManager = viewModel.getCallbackManager();
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                viewModel.signInWithFacebook(loginResult.getAccessToken(), new SignUpCallback() {
                    @Override
                    public void onSuccess() {
                        navController.navigate(R.id.action_signUpFragment_to_homeFragment);
                    }

                    @Override
                    public void onFailure(int errorCode) {
                        Toast.makeText(getContext(), "Xác thực không thành công.", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancel() {
                Toast.makeText(getContext(), "Đăng ký bị hủy.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getContext(), "Đăng ký Facebook không thành công.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInClient googleSignInClient = viewModel.getGoogleSignInClient(getContext(), getString(R.string.default_web_client_id));
            viewModel.signInWithGoogle(data, googleSignInClient, getActivity(), new SignUpCallback() {
                @Override
                public void onSuccess() {
                    navController.navigate(R.id.action_signUpFragment_to_homeFragment);
                }

                @Override
                public void onFailure(int errorCode) {
                    Toast.makeText(getContext(), "Google SignIn không thành công.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            viewModel.getCallbackManager().onActivityResult(requestCode, resultCode, data);
        }
    }
}