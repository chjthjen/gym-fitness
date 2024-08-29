package com.example.gymfitness.fragments.authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gymfitness.R;
import com.example.gymfitness.data.Users;
import com.example.gymfitness.activities.HomeActivity;
import com.example.gymfitness.databinding.FragmentLoginBinding;
import com.facebook.CallbackManager;
import com.example.gymfitness.viewmodels.LoginViewModel ;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

public class LoginFragment extends Fragment {

    // Xai viewmodel
    private LoginViewModel viewModel;
    private FragmentLoginBinding binding;
    private NavController navController;
    private ImageButton imgGG;
    private ImageButton imgFB;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private GoogleSignInClient googleSignInClient;
    private ProgressDialog progressDialog;
    private CallbackManager callbackManager;

    private static final int RC_SIGN_IN = 100;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(requireContext().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        navController = NavHostFragment.findNavController(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeFirebase();
        initializeGoogleSignIn();
        initializeFacebookLogin();
        setupUI();
    }

    private void initializeFirebase() {
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
    }

    private void initializeGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso);
    }

    private void initializeFacebookLogin() {
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                viewModel.handleFacebookAccessToken(loginResult.getAccessToken());  // This method should be in LoginViewModel
            }

            @Override
            public void onCancel() {
                Toast.makeText(getContext(), "Đăng nhập bị hủy.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getContext(), "Đăng nhập Facebook không thành công.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupUI() {
        imgGG = binding.btnGoogle;
        imgFB = binding.btnFacebook;
        progressDialog = new ProgressDialog(getContext());

        imgGG.setOnClickListener(v -> {
            progressDialog.setMessage("Logging in...");
            progressDialog.show();
            Intent signInIntent = googleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        });

        imgFB.setOnClickListener(v ->
                LoginManager.getInstance().logInWithReadPermissions(LoginFragment.this, Arrays.asList("email", "public_profile"))
        );

        TextView tvSignUp = binding.tvSignUp;
        tvSignUp.setOnClickListener(v -> navController.navigate(R.id.action_loginFragment_to_signUpFragment));

        binding.tvForgotPassword.setOnClickListener(v -> navController.navigate(R.id.action_loginFragment_to_forgottenPasswordFragment22));

        Button btnLogin = binding.btnLogin;
        btnLogin.setOnClickListener(v -> {
            String usernameOrEmail = binding.edtUsername.getText().toString().trim();
            String password = binding.edtPassword.getText().toString().trim();
            //goi view model dang nhap
            viewModel.loginWithEmail(usernameOrEmail, password);
        });

        viewModel.getCurrentUser().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                saveUserToDatabase(user.getData());
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
                progressDialog.dismiss();
                getActivity().finish();
            }
        });

        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(getContext(), (CharSequence) error, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss(); // Hide progress dialog if shown
            }
        });
    }
    private void saveUserToDatabase(FirebaseUser user) {
        Users users = new Users();
        users.setUserId(user.getUid());
        users.setName(user.getDisplayName());
        users.setProfile(user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : "default");

        database.getReference().child("Users").child(user.getUid()).setValue(users);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            viewModel.handleGoogleSignInResult(task);  // Call ViewModel method to handle result
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
}

