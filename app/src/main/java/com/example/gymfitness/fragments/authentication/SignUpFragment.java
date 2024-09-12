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
import com.example.gymfitness.data.UserAccount;
import com.example.gymfitness.helpers.ValidationHelpers;
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
        progressDialog.setTitle("Registration");
        progressDialog.setMessage("Registering...");
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
            UserAccount userAccount = new UserAccount(
                    binding.edtEmailOrMobile.getText().toString(),
                    binding.edtPassword.getText().toString(),
                    binding.edtFullname.getText().toString()
            );
            viewModel.setUser(userAccount);

            if (viewModel.checkUserStatus()) {
                Toast.makeText(getActivity(), "Please log out of your account!", Toast.LENGTH_SHORT).show();
            } else {
                progressDialog.show();
                viewModel.signUp(new SignUpCallback() {
                    @Override
                    public void onSuccess() {
                        progressDialog.dismiss();
                        viewModel.saveDB(new SignUpCallback() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(getActivity(), "Registration successful", Toast.LENGTH_LONG).show();
                                navController.navigate(R.id.action_signUpFragment_to_loginFragment);
                            }

                            @Override
                            public void onFailure(int errorCode) {
                                viewModel.deleteUserFromFirebase(new SignUpCallback() {
                                    @Override
                                    public void onSuccess() {
                                        Toast.makeText(getActivity(), "Registration failed. Please try again.", Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onFailure(int errorCode) {
                                        Toast.makeText(getActivity(), "Registration failed. Please try again.", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        });
                    }

                    @Override
                    public void onFailure(int errorCode) {
                        progressDialog.dismiss();
                        if (errorCode == -999) {
                            Toast.makeText(getActivity(), "This Gmail is already used for another account", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getActivity(), "Registration failed. Please try again.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }
    }

    private boolean validateInput() {
        String usernameOrEmail = binding.edtEmailOrMobile.getText().toString().trim();
        String password = binding.edtPassword.getText().toString().trim();

        // Call validation
        return ValidationHelpers.validateInputEmail(
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
                        Toast.makeText(getContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancel() {
                Toast.makeText(getContext(), "Registration canceled.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getContext(), "Facebook registration failed.", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getContext(), "Google Sign-In failed.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            viewModel.getCallbackManager().onActivityResult(requestCode, resultCode, data);
        }
    }
}
